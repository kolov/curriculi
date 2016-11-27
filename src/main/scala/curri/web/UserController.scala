package curri.web

import javax.servlet.http.HttpServletRequest

import curri.domain.{Identity, IdentityProvider, User}
import curri.service.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

@Controller
@RequestMapping(Array("/user"))
class UserController @Autowired()(private val userRepository: UserRepository) {

  val LOG = LoggerFactory.getLogger(getClass)

  val USER: String = "USER"


  @RequestMapping(value = Array(""), method = Array(RequestMethod.GET))
  @ResponseBody
  def current(servletRequest: HttpServletRequest): User = {
    val user = servletRequest.getAttribute(USER).asInstanceOf[User]
    val principal = servletRequest.getUserPrincipal
    if (principal != null) {
      val oauth = principal.asInstanceOf[OAuth2Authentication]
      val details = oauth.getUserAuthentication().getDetails.asInstanceOf[java.util.Map[String,Object]]
      val link = details.get("link").asInstanceOf[String]

      if (link contains ("facebook.com")) {
        val identity = new Identity(IdentityProvider.FACEBOOK,
          details.get("first_name").asInstanceOf[String],
          details.get("last_name").asInstanceOf[String],
          details.get("id").asInstanceOf[String]
        )
        user.identity = identity
      }
    }
    user
  }

  @RequestMapping(value = Array("accepts-cookies"), method = Array(RequestMethod.POST))
  @ResponseBody
  def acceptsCookies(servletRequest: HttpServletRequest): User = {
    val user = servletRequest.getAttribute(USER).asInstanceOf[User]
    user.setAcceptsCookies(true)
    userRepository.save(user)
    user
  }
}