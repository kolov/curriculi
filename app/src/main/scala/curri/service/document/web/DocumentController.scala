package curri.service.document.web

import javax.servlet.http.HttpServletRequest

import curri.service.user.domain.User
import curri.service.user.persist.UserRepository
import curri.web.CookieFilter
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

@Controller
@RequestMapping(Array("/v1/docs"))
class DocumentController @Autowired()(private val userRepository: UserRepository) {

  val LOG = LoggerFactory.getLogger(getClass)


  @RequestMapping(value = Array(""), method = Array(RequestMethod.GET))
  @ResponseBody
  def current(servletRequest: HttpServletRequest): User = {
      servletRequest.getAttribute(CookieFilter.ATTR_USER_NAME).asInstanceOf[User]
  }

  @RequestMapping(value = Array("accepts-cookies"), method = Array(RequestMethod.POST))
  @ResponseBody
  def acceptsCookies(servletRequest: HttpServletRequest): User = {
    val user = servletRequest.getAttribute(CookieFilter.ATTR_USER_NAME).asInstanceOf[User]
    user.setAcceptsCookies(true)
    userRepository.save(user)
    user
  }
}