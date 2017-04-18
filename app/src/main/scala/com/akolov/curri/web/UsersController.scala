package com.akolov.curri.web

import javax.servlet.http.{HttpServletRequest, HttpSession}

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
  def acceptsCookies(req: HttpServletRequest, session: HttpSession): User = {

    val user = req.getAttribute(CookieFilter.ATTR_USER_NAME).asInstanceOf[User]
    val updated = usersClient.acceptsCookies(user.id)
    if (updated != null) {
      session.setAttribute(CookieFilter.ATTR_USER_NAME, updated)
    }
    updated
  }

  @RequestMapping(value = Array(""), method = Array(RequestMethod.GET))
  @ResponseBody
  def user(servletRequest: HttpServletRequest): User =
    servletRequest.getAttribute(CookieFilter.ATTR_USER_NAME).asInstanceOf[User]

}