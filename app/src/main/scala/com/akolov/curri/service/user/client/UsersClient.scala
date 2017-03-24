package com.akolov.curri.service.user.client

import com.akolov.curri.service.user.domain.{Identity, User}
import com.akolov.curri.web.app.FeignConfig
import curri.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestMethod._
import org.springframework.web.bind.annotation.{PathVariable, RequestBody, RequestMapping, RequestParam};

@FeignClient(name = "service-users",
  configuration = Array(classOf[FeignConfig])
)
trait UsersClient {
  @RequestMapping(method = Array(POST), value = Array("/accepts-cookies"))
  def acceptsCookies(@RequestBody user: User): User;

  @RequestMapping(method = Array(GET), value = Array("/byProvider/{provider}/{id}"))
  def findByProviderCodeAndRemoteId(@PathVariable("provider") providerCode: String,
                                    @PathVariable("id") remoteId: String): ResponseEntity[Identity]

  @RequestMapping(method = Array(POST), value = Array("/registerIdentity"))
  def registerIdentity(@RequestBody  identity: Identity): Identity

  @RequestMapping(method = Array(GET), value = Array("/query"))
  def query(@RequestParam("cookie") cookie: String, @RequestParam("create") create: Boolean): User


  @RequestMapping(method = Array(POST), value = Array("/create"))
  def create : User

}

/**
  * Not sure how do handle 404 responses, trying this approch
  *
  * @param usersClient
  */
@Component
class UsersServiceClient @Autowired()(private val usersClient: UsersClient) {
  def create(): User = usersClient.create

  def acceptsCookies(user: User) = usersClient.acceptsCookies(user)

  def findByProviderCodeAndRemoteId(providerCode: String,
                                    remoteId: String): Option[Identity] = {
    try {
      Some(usersClient.findByProviderCodeAndRemoteId(providerCode, remoteId).getBody)
    } catch {
      case _: NotFoundException => None
    }

  }

  def registerIdentity(identity: Identity): Identity = usersClient.registerIdentity(identity)

  def query(cookie: String, create: Boolean) = usersClient.query(cookie, create)

}





