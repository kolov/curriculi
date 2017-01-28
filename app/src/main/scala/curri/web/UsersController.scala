package curri.web

import javax.servlet.http.HttpServletRequest

import curri.client.user.domain.{User, UsersClient}
import org.slf4j.LoggerFactory
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
  * User service interface for front end. User service is NOT routed by Zuul.
  */

@Controller
@RequestMapping(Array("/user"))
@EnableEurekaClient
class UsersController(private val usersClient: UsersClient) {

  val LOG = LoggerFactory.getLogger(getClass)


  @RequestMapping(value = Array("/accepts-cookies"), method = Array(RequestMethod.POST))
  @ResponseBody
  def acceptsCookies(servletRequest: HttpServletRequest): User = {

    val user = servletRequest.getAttribute(CookieFilter.ATTR_USER_NAME).asInstanceOf[User]

    return usersClient.acceptsCookies(user)
  }


}