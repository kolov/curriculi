package curri.tools

import com.fasterxml.jackson.annotation.JsonProperty

/**
  * Created by assen on 28/12/2016.
  */
class Document {

  @JsonProperty("type")
  var documentType: String = _

  @JsonProperty
  var name: String = _

  @JsonProperty
  var public: Boolean = _

  @JsonProperty
  var content: String = _


}
