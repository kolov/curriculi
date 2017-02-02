package curri.service.user.persist

import java.util.Optional

import curri.service.user.domain.User
import org.springframework.data.mongodb.repository.MongoRepository

trait UserRepository extends MongoRepository[User, String] {
  //def findByCookieValue(cookieId: String): User
  def findByCookieValue(cookieId: String): Optional[User]
}