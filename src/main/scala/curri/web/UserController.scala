package curri.web

import javax.servlet.ServletRequest
import javax.servlet.http.{Cookie, HttpServletRequest, HttpServletResponse}

import com.sun.deploy.net.HttpResponse
import curri.domain.{Cookie, User}
import curri.service.{CookieRepository, UserRepository}
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpRequest
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{CookieValue, RequestMapping, RequestMethod, ResponseBody}

@Controller
@RequestMapping(Array("/user"))
class UserController @Autowired()(private val userRepository: UserRepository,
                                  private val cookieRepository: CookieRepository) {

  val LOG = LoggerFactory.getLogger(getClass)

  val USER: String = "USER"


  @RequestMapping(method = Array(RequestMethod.GET))
  @ResponseBody
  def current(@CookieValue(value = "curri") cookieId: String,
              servletRequest: HttpServletRequest,
              servletResponse: HttpServletResponse) {
    val session = servletRequest.getSession(true);
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
}