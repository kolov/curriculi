package curri.domain

import java.beans.Transient
import javax.persistence.{GeneratedValue, Id}

import com.fasterxml.jackson.annotation.{JsonIgnore, JsonProperty}
import com.fasterxml.jackson.module.scala.JsonScalaEnumeration
import curri.identity.IdentityCodesType
import org.springframework.data.mongodb.core.mapping.Document

import scala.beans.BeanProperty

@Document
class Identity {

  @Id
  @GeneratedValue
  @JsonIgnore
  var id: String = _

  @BeanProperty
  @JsonProperty("provider")
  @JsonScalaEnumeration(classOf[IdentityCodesType])
  //var provider: IdentityProvider.IdentityProvider = _
  // Scala Enumeration doesn't work out of the box with MongoDB, use string
  var providerCode: String = _

  @BeanProperty
  @JsonProperty("firstName")
  var firstName: String = _

  @BeanProperty
  @JsonProperty("lastName")
  var lastName: String = _

  @BeanProperty
  @JsonProperty("name")
  var name: String = _

  @BeanProperty
  @JsonIgnore
  var link: String = _

  @BeanProperty
  @JsonIgnore
  var remoteId: String = _

}
