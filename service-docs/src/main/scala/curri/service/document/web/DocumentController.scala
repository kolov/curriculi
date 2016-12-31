package curri.service.document.web

import curri.service.document.persist.DocumentRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, ResponseBody}

@Controller
@RequestMapping(Array("/v1/docs"))
class DocumentController @Autowired()(private val documentRepository: DocumentRepository) {

  val LOG = LoggerFactory.getLogger(getClass)

  @RequestMapping(Array("/hearbeat"))
  @ResponseBody
  def heartbeat(): String = "OK"

}