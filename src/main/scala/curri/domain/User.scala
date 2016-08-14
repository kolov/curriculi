package curri.domain

import javax.persistence.Id
import javax.persistence.GeneratedValue
import java.lang.Long
import java.util.UUID
import javax.persistence.Entity

import scala.beans.BeanProperty
import org.hibernate.validator.constraints.NotEmpty

@Entity
class User() {

  cookieValue = UUID.randomUUID().toString

  acceptsCookies = false;

  @Id
  @GeneratedValue
  @BeanProperty
  var id: String = _

  @BeanProperty
  var cookieValue: String = _

  @BeanProperty
  var acceptsCookies: Boolean = _

}