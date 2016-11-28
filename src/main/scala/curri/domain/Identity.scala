package curri.domain

import javax.persistence.{GeneratedValue, Id}

import com.fasterxml.jackson.annotation.{JsonIgnore, JsonProperty}
import curri.domain.IdentityProvider.IdentityProvider
import org.springframework.data.mongodb.core.mapping.Document

import scala.beans.BeanProperty

@Document
class Identity {

  @Id
  @GeneratedValue
  var id: String = _

  @BeanProperty
  @JsonProperty("provider")
  var provider: String = _

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
