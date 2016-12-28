package curri.service.document.persist

import curri.service.document.domain.Document
import org.springframework.data.mongodb.repository.MongoRepository

trait DocumentRepository extends MongoRepository[Document, String] {
  def findByOwnerUser(cookieId: String): Document
}