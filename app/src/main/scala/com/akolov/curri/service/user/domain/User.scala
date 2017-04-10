package com.akolov.curri.service.user.domain

import java.util.UUID

import com.fasterxml.jackson.annotation.{JsonIgnore, JsonProperty}
import org.springframework.data.annotation.Id

import scala.beans.BeanProperty


class User() extends Serializable {

  @Id
  @BeanProperty
  @JsonProperty("id")
  var id: String = _

  @BeanProperty
  @JsonProperty("cookieValue")
  var cookieValue: String = _

  @BeanProperty
  @JsonProperty("acceptsCookies")
  var acceptsCookies: Boolean = _

  @BeanProperty
  @JsonProperty("identity")
  var identity: Identity = _

  def wipe = {
    cookieValue = UUID.randomUUID().toString
    acceptsCookies = false
    identity = null
  }

}