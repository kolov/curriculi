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

  val COOKIE_NAME: String = "curri"
  val COOKIE_AGE: Int = 30 * 24 * 60 * 60 // 30 days

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

  @throws[IOException]
  @throws[ServletException]
  def doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain): Unit = {

    def getNonEmptyCookies: Array[Cookie] = {
      var cookies: Array[Cookie] =
        request.asInstanceOf[HttpServletRequest].getCookies
      if (cookies == null) {
        cookies = new Array[Cookie](0)
      }
      cookies
    }

    val cookies: Array[Cookie] = getNonEmptyCookies

    var user = cookies.find(_.getName.equals(COOKIE_NAME))
      .map(cookie => cookie.getValue)
      .map(v => userRepository.findByCookieValue(v))
      .orNull

    if (user == null) {
      user = createUser()
    }

    request.setAttribute("USER", user)

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
    val cookie: Cookie = new Cookie(COOKIE_NAME, user.getCookieValue)
    cookie.setMaxAge(COOKIE_AGE)
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
