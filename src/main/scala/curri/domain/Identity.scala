package curri.domain

import javax.persistence.{GeneratedValue, Id}

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.scala.JsonScalaEnumeration
import org.springframework.data.mongodb.core.mapping.Document

import scala.beans.BeanProperty

@Document
class Identity {

  @Id
  @GeneratedValue
  var id: String = _

  @BeanProperty
  @JsonProperty("provider")
  @JsonScalaEnumeration(classOf[IdentityProviderType])
  var provider: IdentityProvider.IdentityProvider = _

  @BeanProperty
  @JsonProperty("first-name")
  var firstName: String = _

  @BeanProperty
  @JsonProperty("last-name")
  var lastName: String = _

  @BeanProperty
  @JsonProperty("id")
  var providerId: String = _

}
