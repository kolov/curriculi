package curri.service

import curri.domain.Cookie
import org.springframework.data.mongodb.repository.MongoRepository

trait CookieRepository extends MongoRepository[Cookie, String]