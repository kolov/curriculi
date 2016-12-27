package curri.web

import java.io.IOException
import java.security.Principal
import javax.servlet._
import javax.servlet.http.{Cookie, HttpServletRequest, HttpServletResponse}

import curri.domain.User
import curri.identity.AllProviders
import curri.service.{IdentityRepository, UserRepository}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.stereotype.Component

@Component
class CookieFilter @Autowired()(private val userRepository: UserRepository,
                                private val identityRepository: IdentityRepository) extends Filter {

  val LOG = LoggerFactory.getLogger(getClass)


  @Autowired
  var providers: AllProviders = _


  @throws[ServletException]
  def init(filterConfig: FilterConfig) {
  }


  def createUser(): User = {
    val user = new User()
    user.wipe
    userRepository.save(user)
    user
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
    val reqCookie: Option[String] = curriCookie(request)
    var user: User = request.getSession(true).getAttribute(CookieFilter.ATTR_USER_NAME).asInstanceOf[User]

    if (user == null && reqCookie.isDefined) {
      user = userRepository.findByCookieValue(reqCookie.get)
    }

    if (user == null) {
      user = createUser()
    }

    request.setAttribute(CookieFilter.ATTR_USER_NAME, user)
    request.getSession().setAttribute(CookieFilter.ATTR_USER_NAME, user)

    val principal = request.asInstanceOf[HttpServletRequest].getUserPrincipal
    if (user.getIdentity == null) {
      if (principal != null) {
        linkIdentityToUser(user, principal)
      }
    } else {
      if (principal == null) {
        user.wipe
        userRepository.save(user)
      }
    }

    setCookieInResponse(response, user)

    chain.doFilter(request, response)

  }

  def setCookieInResponse(response: ServletResponse, user: User): Unit = {
    val cookie: Cookie = new Cookie(CookieFilter.COOKIE_NAME, user.getCookieValue)
    cookie.setMaxAge(CookieFilter.COOKIE_AGE)
    cookie.setPath("/")
    response.asInstanceOf[HttpServletResponse].addCookie(cookie)
  }

  def linkIdentityToUser(user: User, principal: Principal): Any = {
    val oauth = principal.asInstanceOf[OAuth2Authentication]
    val identity = providers.getProviders()
      .find(_.canHandle(oauth))
      .map(_.createIdentity(oauth))
      .getOrElse(null)

    if (identity != null) {
      val existing = identityRepository.findByProviderCodeAndRemoteId(identity.providerCode, identity.remoteId)
      if (existing == null) {
        user.setIdentity(identityRepository.save(identity))
      } else {
        user.setIdentity(existing)
      }
      userRepository.save(user)
    } else {
      LOG.error("Could not process identity " + oauth)
    }

  }

  def destroy {
  }
}

object CookieFilter {
  val COOKIE_NAME: String = "curri"
  val COOKIE_AGE: Int = 30 * 24 * 60 * 60 // 30 days

  val ATTR_USER_NAME = classOf[CookieFilter].getName + ".user"

}