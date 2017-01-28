package curri.service.user.web

import javax.servlet.http.HttpServletRequest

import curri.service.user.domain.User
import curri.service.user.persist.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation._

@Controller
@RequestMapping(Array("/"))
class UserController @Autowired()(private val userRepository: UserRepository) {

  val LOG = LoggerFactory.getLogger(getClass)

  @RequestMapping(value = Array("/byCookie"), method = Array(RequestMethod.GET))
  @ResponseBody
  def findByCookieValue(@RequestParam cookie: String): User = {
    return userRepository.findByCookieValue(cookie)
  }

  @RequestMapping(value = Array("/registerUser"), method = Array(RequestMethod.POST))
  @ResponseBody
  def registerUser(@RequestBody user: User): User = {
    userRepository.save(user)
    user
  }

  @RequestMapping(value = Array(""), method = Array(RequestMethod.GET))
  @ResponseBody
  def current(servletRequest: HttpServletRequest): User = {
    servletRequest.getAttribute("curriuser").asInstanceOf[User]
  }

  @RequestMapping(value = Array("/accepts-cookies"), method = Array(RequestMethod.POST))
  @ResponseBody
  def acceptsCookies(@RequestBody user: User): User = {
    user.setAcceptsCookies(true)
    userRepository.save(user)
    user
  }
}