package curri.client.user.domain

import java.util.UUID

import com.fasterxml.jackson.annotation.{JsonIgnore, JsonProperty}
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.{DBRef, Document}

import scala.beans.BeanProperty

@Document
class User() extends Serializable {

  @Id
  @JsonIgnore
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

  def wipe = {
    cookieValue = UUID.randomUUID().toString
    acceptsCookies = false
    identity = null
  }

}