package curri.domain

import java.lang.Long
import javax.persistence.{GeneratedValue, Id}

import scala.beans.BeanProperty


class Cookie() {

  @Id
  @GeneratedValue
  @BeanProperty
  var id: Long = _

  @BeanProperty
  var value: String = _

  @BeanProperty
  var userId: String = _

  def this( userId: String) {
    this()
    this.userId = userId
  }

}