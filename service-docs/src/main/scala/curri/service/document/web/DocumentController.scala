package curri.service.document.web

import javax.servlet.http.HttpServletRequest

import curri.service.document.persist.DocumentRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

@Controller
@RequestMapping(Array("/v1/docs"))
class DocumentController @Autowired()(private val documentRepository: DocumentRepository) {

  val LOG = LoggerFactory.getLogger(getClass)

}