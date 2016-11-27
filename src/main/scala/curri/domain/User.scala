package curri.domain

import java.util.UUID
import javax.persistence.{GeneratedValue, Id}

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
  var cookieValue: String = _

  @DBRef
  @BeanProperty
  var identity: Identity = _

  @BeanProperty
  var acceptsCookies: Boolean = _

}