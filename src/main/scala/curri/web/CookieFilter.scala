package curri.web

import java.io.IOException
import javax.servlet._
import javax.servlet.http.{Cookie, HttpServletRequest, HttpServletResponse}

import curri.domain.{Identity, IdentityProvider, User}
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
      userRepository.save(user)
    }
    request.setAttribute("USER", user)

    if (user.getIdentity == null) {
      val principal = request.asInstanceOf[HttpServletRequest].getUserPrincipal
      if (principal != null) {
        val oauth = principal.asInstanceOf[OAuth2Authentication]
        val details = oauth.getUserAuthentication.getDetails.asInstanceOf[java.util.Map[String, Object]]
        val link = details.get("link").asInstanceOf[String]

        if (link contains "facebook.com") {
          val identity = new Identity()
          identity.setProvider(IdentityProvider.FACEBOOK.toString)
          identity.setFirstName(details.get("first-name").asInstanceOf[String])
          identity.setLastName(details.get("last-name").asInstanceOf[String])
          identity.setRemoteId(details.get("id").asInstanceOf[String])
          user.identity = identity
        }

      }
      if (user.identity != null) {
        identityRepository.save(user.identity)
        userRepository.save(user)
      }
    }

    val cookie: Cookie = new Cookie(COOKIE_NAME, user.getCookieValue)
    cookie.setMaxAge(COOKIE_AGE)
    cookie.setPath("/")
    response.asInstanceOf[HttpServletResponse].addCookie(cookie)

    chain.doFilter(request, response)

  }

  def destroy {
  }
}
