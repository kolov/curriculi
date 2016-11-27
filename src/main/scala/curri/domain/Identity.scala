package curri.domain

import javax.persistence.{GeneratedValue, Id}

import curri.domain.IdentityProvider.IdentityProvider
import org.springframework.data.mongodb.core.mapping.Document

import scala.beans.BeanProperty

@Document
class Identity(
                @BeanProperty IdentityProvider: IdentityProvider,
                @BeanProperty firstName: String,
                @BeanProperty lastName: String,
                @BeanProperty providerId: String) {

  @Id
  @GeneratedValue
  var id: String = _

}
