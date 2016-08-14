package curri.web

import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import curri.domain.User
import curri.service.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{CookieValue, RequestMapping, RequestMethod, ResponseBody}

@Controller
@RequestMapping(Array("/user"))
class UserController @Autowired()(private val userRepository: UserRepository ) {

  val LOG = LoggerFactory.getLogger(getClass)

  val USER: String = "USER"


  @RequestMapping(method = Array(RequestMethod.GET))
  @ResponseBody
  def current(@CookieValue(value = "curri") cookieId: String,
              servletRequest: HttpServletRequest,
              servletResponse: HttpServletResponse) {
    val session = servletRequest.getSession(true);
    return session.getAttribute(USER).asInstanceOf[User]

  }
}