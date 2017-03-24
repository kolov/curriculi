package com.akolov.curri.web

import javax.servlet.http.HttpServletRequest

import com.akolov.curri.service.user.client.UsersClient
import com.akolov.curri.service.user.domain.User
import com.akolov.curri.web.app.CookieFilter
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
  * User service interface for front end. User service is NOT routed by Zuul.
  */

@Controller
@RequestMapping(Array("/current/user"))
class UsersController(private val usersClient: UsersClient) {

  val LOG = LoggerFactory.getLogger(getClass)

  @RequestMapping(value = Array("/accepts-cookies"), method = Array(RequestMethod.POST))
  @ResponseBody
  def acceptsCookies(servletRequest: HttpServletRequest): User = {

    val user = servletRequest.getAttribute(CookieFilter.ATTR_USER_NAME).asInstanceOf[User]
    return usersClient.acceptsCookies(user)
  }

  @RequestMapping(value = Array(""), method = Array(RequestMethod.GET))
  @ResponseBody
  def user(servletRequest: HttpServletRequest): User =
    servletRequest.getAttribute(CookieFilter.ATTR_USER_NAME).asInstanceOf[User]

}