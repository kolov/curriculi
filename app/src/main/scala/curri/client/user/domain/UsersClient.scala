package curri.client.user.domain


import curri.app.FeignConfig
import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestMethod._
import org.springframework.web.bind.annotation.{PathVariable, RequestBody, RequestMapping, RequestParam};

@FeignClient(name = "service-users",
  configuration = Array(classOf[FeignConfig])
  // , fallback = classOf[UsersClientCallback]
)
trait UsersClient {
  @RequestMapping(method = Array(POST), value = Array("/accepts-cookies"))
  def acceptsCookies(@RequestBody user: User): User;

  @RequestMapping(method = Array(GET), value = Array("/byProvider/{provider}/{id}"))
  def findByProviderCodeAndRemoteId(@PathVariable("provider") providerCode: String,
                                    @PathVariable("id") remoteId: String): ResponseEntity[Identity];

  @RequestMapping(method = Array(POST), value = Array("/registerIdentity"))
  def saveIdentity(identity: Identity): Identity

  @RequestMapping(method = Array(GET), value = Array("/byCookie"))
  def findByCookieValue(@RequestParam("cookie") cookie: String): User;


  @RequestMapping(method = Array(POST), value = Array("/registerUser"))
  def registerUser(@RequestBody user: User): User;
}


@Component
class UsersClientCallback extends UsersClient {
  @RequestMapping(method = Array(POST), value = Array("/accepts-cookies"))
  override def acceptsCookies(user: User): User = null

  @RequestMapping(method = Array(GET), value = Array("/byProvider/{provider}/{id}"))
  override def findByProviderCodeAndRemoteId(providerCode: String, remoteId: String): ResponseEntity[Identity] = null

  @RequestMapping(method = Array(POST), value = Array("/registerIdentity"))
  override def saveIdentity(identity: Identity): Identity = null

  @RequestMapping(method = Array(GET), value = Array("/byCookie"))
  override def findByCookieValue(cookie: String): User = null

  @RequestMapping(method = Array(POST), value = Array("/registerUser"))
  override def registerUser(user: User): User = null
}



