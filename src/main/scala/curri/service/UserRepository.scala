package curri.service

import curri.domain.User
import org.springframework.data.mongodb.repository.MongoRepository

trait UserRepository extends MongoRepository[User, String]