package curri.domain

import java.beans.Transient
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
  //var provider: IdentityProvider.IdentityProvider = _
  // Scala Enumeration doesn't work out of the box with MongoDB, use string
  var provider: String = _

  @BeanProperty
  @JsonProperty("firstName")
  var firstName: String = _

  @BeanProperty
  @JsonProperty("lastName")
  var lastName: String = _

  @BeanProperty
  @JsonProperty("remoteId")
  var remoteId: String = _

}
