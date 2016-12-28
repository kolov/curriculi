package curri.tools

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

/**
  * Created by assen on 28/12/2016.
  */

@Component
class DocumentsClient {

  @Value("${curri.service.documents}")
  var documentsService: String = _

  def post(header: Document): String = {
    return new RestTemplate().postForEntity(documentsService, header, classOf[String])
      .getStatusCode.toString
  }

}
