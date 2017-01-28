package curri.client.user.domain


import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.web.bind.annotation.{PathVariable, RequestBody, RequestMapping, RequestParam}
import org.springframework.web.bind.annotation.RequestMethod._;

@FeignClient("service-users")
trait UsersClient {
  @RequestMapping(method = Array(POST), value = Array("/accepts-cookies"))
  def acceptsCookies(@RequestBody user: User): User;

  @RequestMapping(method = Array(GET), value = Array("/byProvider/{provider}/{id}"))
  def findByProviderCodeAndRemoteId(@PathVariable("provider") providerCode: String,
                                    @PathVariable("id") remoteId: String): Identity;

  @RequestMapping(method = Array(POST), value = Array("/registerIdentity"))
  def saveIdentity(identity: Identity): Identity

  @RequestMapping(method = Array(GET), value = Array("/byCookie"))
  def findByCookieValue(@RequestParam("cookie") cookie: String): User;


  @RequestMapping(method = Array(POST), value = Array("/registerUser"))
  def registerUser(@RequestBody user: User): User;
}
