package curri.service.document.domain

import org.springframework.data.annotation.Id

import com.fasterxml.jackson.annotation.{JsonIgnore, JsonProperty}

import scala.beans.BeanProperty

@Document
class Document() {

  @Id
  @JsonIgnore
  var id: String = _

  @BeanProperty
  @JsonProperty("content")
  var content: String = _

  @BeanProperty
  @JsonIgnore
  var ownerUser: String = _

  @BeanProperty
  @JsonIgnore
  var ownerGroup: String = _

  @BeanProperty
  var public: Boolean = _

}