package curri.web

import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import curri.domain.User
import curri.service.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{CookieValue, RequestMapping, RequestMethod, ResponseBody}

@Controller
@RequestMapping(Array("/v1.0/user"))
class UserController @Autowired()(private val userRepository: UserRepository) {

  val LOG = LoggerFactory.getLogger(getClass)

  val USER: String = "USER"


  @RequestMapping(value = Array(""), method = Array(RequestMethod.GET))
  @ResponseBody
  def current(servletRequest: HttpServletRequest): User =  {
    servletRequest.getAttribute(USER).asInstanceOf[User]
  }

  @RequestMapping(value = Array("accepts-cookies"), method = Array(RequestMethod.POST))
  @ResponseBody
  def acceptsCookies(servletRequest: HttpServletRequest): User = {
    val user = servletRequest.getAttribute(USER).asInstanceOf[User]
    user.setAcceptsCookies(true)
    userRepository.save(user)
    return user
  }
}