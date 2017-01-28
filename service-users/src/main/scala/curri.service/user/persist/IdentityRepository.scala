package curri.service.user.persist

import curri.service.user.domain.Identity
import org.springframework.data.mongodb.repository.MongoRepository

trait IdentityRepository extends MongoRepository[Identity, String] {
  def findByProviderCodeAndRemoteId(providerCode: String, remoteId: String) : Identity
}