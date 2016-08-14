package curri.web

import java.io.IOException
import javax.servlet._
import javax.servlet.http.{Cookie, HttpServletRequest, HttpServletResponse}

import curri.domain.User
import curri.service.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CookieFilter @Autowired()(private val userRepository: UserRepository) extends Filter {

  val LOG = LoggerFactory.getLogger(getClass)

  val COOKIE_NAME: String = "curri"
  val COOKIE_AGE: Int = 30 * 24 * 60 * 60

  @throws[ServletException]
  def init(filterConfig: FilterConfig) {
  }


  @throws[IOException]
  @throws[ServletException]
  def doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain): Unit = {
    val cookies: Array[Cookie] = request.asInstanceOf[HttpServletRequest]
      .getCookies

    var cookieValue: Option[String] = None
    if (cookies != null ) {
      cookieValue = cookies.find(_.getName.equals(COOKIE_NAME)).map(_.getValue)
    }

    var allSet: Boolean = false
    var user: User = null
    if (cookieValue.isDefined) {
      val maybeUser: User = userRepository.findByCookieValue(cookieValue.get)
      if (maybeUser != null) {
        allSet = true
        user = maybeUser
      }
    }

    if (!allSet) {
      user = new User()
      userRepository.save(user)
    }
    request.setAttribute("USER", user)
    chain.doFilter(request, response)
    val cookie: Cookie = new Cookie(COOKIE_NAME, user.getCookieValue)
    cookie.setMaxAge(COOKIE_AGE)
    response.asInstanceOf[HttpServletResponse].addCookie(cookie)

  }

  def destroy {
  }
}
