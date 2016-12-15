package curri.domain

import java.util.UUID
import javax.persistence.{GeneratedValue, Id}

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.{DBRef, Document}

import scala.beans.BeanProperty

@Document
class User() {

  @Id
  @GeneratedValue
  var id: String = _

  @BeanProperty
  @Indexed
  @JsonProperty("cookieValue")
  var cookieValue: String = _

  @BeanProperty
  @JsonProperty("acceptsCookies")
  var acceptsCookies: Boolean = _

  @DBRef
  @BeanProperty
  @JsonProperty("identity")
  var identity: Identity = _

  def unknown = {
    cookieValue = UUID.randomUUID().toString
    acceptsCookies = false
  }
}