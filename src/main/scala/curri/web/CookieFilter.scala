package curri.web

import java.io.IOException
import javax.servlet._
import javax.servlet.http.HttpServletRequest

import curri.domain.User
import curri.service.{CookieRepository, UserRepository}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CookieFilter @Autowired()(private val userRepository: UserRepository,
                                private val cookieRepository: CookieRepository) extends Filter {

  val LOG = LoggerFactory.getLogger(getClass)

  private val USER: String = "USER"
  private val KNOWN_COOKIE: String = "USER"

  @throws[ServletException]
  def init(filterConfig: FilterConfig) {
  }

  @throws[IOException]
  @throws[ServletException]
  def doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain): Unit = {
    val session = request.asInstanceOf[HttpServletRequest].getSession(true);
    val cookieValue = request.asInstanceOf[HttpServletRequest]
      .getCookies.filter(_.getName.equals("curri"))
      .map(_.getValue).head

    if (cookieValue != null && cookieValue.equals(session.getAttribute(KNOWN_COOKIE))) {
      chain.doFilter(request, response)
      return
    }

    var user: User = session.getAttribute(USER).asInstanceOf[User]
    if (user != null) {
      return user
    } else if (cookieId != null) {
      val cookie = cookieRepository.findOne(cookieId)
      if (cookie == null) {
        LOG.warn("Received cookie with unknown value")
      } else {
        user = userRepository.findOne(cookie.userId)
        if (user != null) {
          session.setAttribute(USER, user)
          return user
        } else {
          LOG.warn("Cookie with unknown user id")
        }
      }
    }
    // no success so far
    user = new User
    userRepository.save(user)
    val cookie = new Cookie(user.id)
    cookieRepository.save(cookie)
    servletResponse.addCookie(newCookie)
  }

  def destroy {
  }
}
