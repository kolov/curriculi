package com.akolov.curri.web.app

import java.io.IOException
import java.security.Principal
import javax.servlet._
import javax.servlet.http.{Cookie, HttpServletRequest, HttpServletResponse}

import com.akolov.curri.service.user.client.UsersServiceClient
import com.akolov.curri.service.user.domain.User
import com.akolov.curri.web.oauth.AllProviders
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.stereotype.Component

@Component
class CookieFilter @Autowired()(private val usersClient: UsersServiceClient) extends Filter {

  val LOG = LoggerFactory.getLogger(getClass)


  @Autowired
  var providers: AllProviders = _


  @throws[ServletException]
  def init(filterConfig: FilterConfig) {
  }


  def curriCookie(request: HttpServletRequest): Option[String] = {
    val cookies: Array[Cookie] = request.asInstanceOf[HttpServletRequest].getCookies
    if (cookies != null) {
      cookies.find(_.getName.equals(CookieFilter.COOKIE_NAME))
        .map(cookie => cookie.getValue).orElse(None)
    } else {
      None
    }
  }

  @throws[IOException]
  @throws[ServletException]
  def doFilter(req: ServletRequest, response: ServletResponse, chain: FilterChain): Unit = {

    val request: HttpServletRequest = req.asInstanceOf[HttpServletRequest]

    var user: User = null

    getUserFromSession(request) match {
      case None =>
        curriCookie(request) match {
          case Some(cookie) => user = usersClient.query(cookie, true)
          case None =>
            user = usersClient.create()
            setCookieInResponse(response, user)
        }
        putUserInSession(request, user)
      case Some(u) => user = u

    }
    request.setAttribute(CookieFilter.ATTR_USER_NAME, user)

    val principal = request.asInstanceOf[HttpServletRequest].getUserPrincipal
    if (user.getIdentity == null && principal != null) {
      //just logged in
      linkIdentityToUser(user, principal)
    } else if (user.getIdentity != null && principal == null) {
      // just logged out
      user = usersClient.create()
    }

    chain.doFilter(request, response)

  }

  def getUserFromSession(request: HttpServletRequest) = {
    val u = request.getSession(true).getAttribute(CookieFilter.ATTR_USER_NAME).asInstanceOf[User]
    if (u != null) Some(u) else None
  }

  def putUserInSession(request: HttpServletRequest, user: User) =
    request.getSession(true).setAttribute(CookieFilter.ATTR_USER_NAME, user)

  def setCookieInResponse(response: ServletResponse, user: User): Unit = {
    val cookie: Cookie = new Cookie(CookieFilter.COOKIE_NAME, user.getCookieValue)
    cookie.setMaxAge(CookieFilter.COOKIE_AGE)
    cookie.setPath("/")
    response.asInstanceOf[HttpServletResponse].addCookie(cookie)
  }

  /**
    * Called after a uprincipal appears in the request but the user has no identity
    * - i.e. after user logs in.
    * If this identity is already known, create a new session users with it.
    * If it is new, save it, attach it to current user and continu
    *
    * @param user
    * @param principal
    */
  def linkIdentityToUser(user: User, principal: Principal): Unit = {
    val oauth = principal.asInstanceOf[OAuth2Authentication]
    val identity = providers.getProviders()
      .find(_.canHandle(oauth))
      .map(_.createIdentity(oauth))

    identity match {
      case None => LOG.error("Don't know how to process Oauth: " + oauth)
        return
      case Some(reqIdentityValue) =>
        val existing = usersClient.findByProviderCodeAndRemoteId(reqIdentityValue.providerCode,
          reqIdentityValue.remoteId)

        existing match {
          case Some(identotyValue) => user.setIdentity(identotyValue)
          case None => user.setIdentity(usersClient.registerIdentity(reqIdentityValue))
        }
    }

  }

  def destroy {
  }
}

object CookieFilter {
  val COOKIE_NAME: String = "curri"
  val COOKIE_AGE: Int = 30 * 24 * 60 * 60 // 30 days

  val ATTR_USER_NAME = "curriuser"

}