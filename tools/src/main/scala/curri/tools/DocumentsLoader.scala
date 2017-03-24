package curri.tools

import java.io.File

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import scala.io.Source

@Component
class DocumentsLoader {

  @Autowired
  var client : DocumentsClient = _

  def loadDocument(folder: String, baseName: String, documentType: String): Unit = {
    val json = Source.fromFile(new File(folder + "/" + baseName + ".json")).mkString
    val content = Source.fromFile(new File(folder + "/" + baseName + "." + documentType)).mkString
    val jsonMapper = new ObjectMapper()
    val header = jsonMapper.readValue(json, classOf[Document])
    header.content = content

//    val res : String = client.post(header)

    System.out.println("POSTED " + baseName + ", response= " + 2)
  }

  def loadDocuments(folder: String, documentType: String): Unit = {
    val file = new File(folder)
    if (!file.isDirectory || !file.exists()) {
      throw new IllegalArgumentException("No such filder " + folder)
    }
    file.listFiles
      .filter(f => f.isFile & f.getName.endsWith(".json"))
      .map(_.getName)
      .map(n => n.substring(0, n.length - ".json".length))
      .foreach(loadDocument(folder, _, documentType))
  }

}
