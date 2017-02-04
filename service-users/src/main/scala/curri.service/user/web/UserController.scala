package curri.service.user.web

import java.util.Optional

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

  private def notFoundResponse[T]() = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null.asInstanceOf[T])

  private def foundResponse[T](entity: T) = ResponseEntity.status(HttpStatus.OK).body(entity)

  private def entityOrNotFound[T](entity: Option[T]): ResponseEntity[T] = {
    entity match {
      case Some(e) => foundResponse(e)
      case None => notFoundResponse()
    }

  }

  def toOption[T](opt: Optional[T]): Option[T] = if (opt.isPresent) Some(opt.get()) else None

  @RequestMapping(value = Array("/query"), method = Array(RequestMethod.GET))
  @ResponseBody
  def query(@RequestParam cookie: String,
            @RequestParam create: Boolean): ResponseEntity[User] = {
    toOption(userRepository.findByCookieValue(cookie)) match {
      case Some(user) => foundResponse(user)
      case None => if (create) foundResponse(createNewUser()) else notFoundResponse()
    }
  }

  private def createNewUser() = {
    val user = new User
    user.wipe
    userRepository.save(user)
    user
  }

  @RequestMapping(value = Array("/create"), method = Array(RequestMethod.POST))
  @ResponseBody
  def create(): User = createNewUser

  @RequestMapping(value = Array("/registerIdentity"), method = Array(RequestMethod.POST))
  @ResponseBody
  def registerIdentity(@RequestBody identity: Identity): Identity = {
    identityRepository.save(identity)
    identity
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
                                    @PathVariable("id") remoteId: String): ResponseEntity[Identity] =
    entityOrNotFound(
      toOption(
        identityRepository.findByProviderCodeAndRemoteId(providerCode, remoteId)))

}