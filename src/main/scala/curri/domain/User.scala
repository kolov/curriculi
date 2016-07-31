package curri.domain

import javax.persistence.Id
import javax.persistence.GeneratedValue
import java.lang.Long
import javax.persistence.Entity
import scala.beans.BeanProperty
import org.hibernate.validator.constraints.NotEmpty


class User() {

  @Id
  @GeneratedValue
  @BeanProperty
  var id: Long = _

  @BeanProperty
  var name: String = _

  @BeanProperty
  var firstName: String = _

  @BeanProperty
  var lastName: String = _

  def this(name: String, firstName: String, lastName: String) {
    this()
    this.name = name;
    this.firstName = firstName;
    this.lastName = lastName;
  }

}