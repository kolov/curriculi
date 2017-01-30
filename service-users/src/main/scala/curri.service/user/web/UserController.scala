package curri.service.user.web

import curri.service.user.domain.{Identity, User}
import curri.service.user.persist.{IdentityRepository, UserRepository}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMethod.GET
import org.springframework.web.bind.annotation._

@Controller
@RequestMapping(Array("/"))
class UserController @Autowired()(private val userRepository: UserRepository,
                                  private val identityRepository: IdentityRepository) {

  val LOG = LoggerFactory.getLogger(getClass)

  def entityOrNotFound[T](entity: T): ResponseEntity[T] = {
    if (entity == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null.asInstanceOf[T])
    } else {
      return ResponseEntity.status(HttpStatus.OK).body(entity)
    }
  }

  @RequestMapping(value = Array("/byCookie"), method = Array(RequestMethod.GET))
  @ResponseBody
  def findByCookieValue(@RequestParam cookie: String): Any = {
    val user = userRepository.findByCookieValue(cookie)
    if (user == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
    } else {
      user
    }
  }

  @RequestMapping(value = Array("/registerUser"), method = Array(RequestMethod.POST))
  @ResponseBody
  def registerUser(@RequestBody user: User): User = {
    userRepository.save(user)
    user
  }


  @RequestMapping(value = Array("/accepts-cookies"), method = Array(RequestMethod.POST))
  @ResponseBody
  def acceptsCookies(@RequestBody user: User): User = {
    user.setAcceptsCookies(true)
    userRepository.save(user)
    user
  }


  @RequestMapping(method = Array(GET), value = Array("/byProvider/{provider}/{id}"))
  @ResponseBody
  def findByProviderCodeAndRemoteId(@PathVariable("provider") providerCode: String,
                                    @PathVariable("id") remoteId: String): ResponseEntity[Identity] = {
    val identity = identityRepository.findByProviderCodeAndRemoteId(providerCode, remoteId)
    entityOrNotFound(identity)
  }
}