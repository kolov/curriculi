package curri.web

import java.io.IOException
import javax.servlet._
import javax.servlet.http.{Cookie, HttpServletRequest, HttpServletResponse}

import curri.domain.{Identity, User}
import curri.identity.{AllProviders, Codes}
import curri.service.{IdentityRepository, UserRepository}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.stereotype.Component

@Component
class CookieFilter @Autowired()(private val userRepository: UserRepository,
                                private val identityRepository: IdentityRepository) extends Filter {

  val LOG = LoggerFactory.getLogger(getClass)

  val COOKIE_NAME: String = "curri"
  val COOKIE_AGE: Int = 30 * 24 * 60 * 60 // 30 days

  @Autowired
  var providers: AllProviders = _


  @throws[ServletException]
  def init(filterConfig: FilterConfig) {
  }


  @throws[IOException]
  @throws[ServletException]
  def doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain): Unit = {
    val cookies: Array[Cookie] =
      request.asInstanceOf[HttpServletRequest].getCookies

    var cookieValue: Option[String] = None
    if (cookies != null) {
      cookieValue = cookies.find(_.getName.equals(COOKIE_NAME)).map(_.getValue)
    }

    var user: User = null
    if (cookieValue.isDefined) {
      user = userRepository.findByCookieValue(cookieValue.get)
    }

    if (user == null) {
      user = new User()
      user.newUser
      userRepository.save(user)

      val cookie: Cookie = new Cookie(COOKIE_NAME, user.getCookieValue)
      cookie.setMaxAge(COOKIE_AGE)
      cookie.setPath("/")
      response.asInstanceOf[HttpServletResponse].addCookie(cookie)
    }
    request.setAttribute("USER", user)

    if (user.getIdentity == null) {
      val principal = request.asInstanceOf[HttpServletRequest].getUserPrincipal
      if (principal != null) {
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
          // log error
        }
      }
    }


    chain.doFilter(request, response)

  }

  def destroy {
  }
}
