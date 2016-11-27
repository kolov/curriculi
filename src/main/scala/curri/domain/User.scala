package curri.domain

import java.util.UUID
import javax.persistence.{GeneratedValue, Id}

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.{DBRef, Document}

import scala.beans.BeanProperty

@Document
class User() {

  cookieValue = UUID.randomUUID().toString
  acceptsCookies = false

  @Id
  @GeneratedValue
  var id: String = _

  @BeanProperty
  @Indexed
  @JsonProperty("cookie-value")
  var cookieValue: String = _

  @BeanProperty
  @JsonProperty("accepts-cookies")
  var acceptsCookies: Boolean = _


  @DBRef
  @BeanProperty
  @JsonProperty("identity")
  var identity: Identity = _
}