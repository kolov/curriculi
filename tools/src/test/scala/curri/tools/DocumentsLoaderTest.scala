package curri.tools

import org.junit.runner.RunWith
import org.junit.{Before, Test}
import org.mockito.{InjectMocks, Matchers, Mock, Mockito}
import org.mockito.runners.MockitoJUnitRunner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner

/**
  * Created by assen on 28/12/2016.
  */
@RunWith(classOf[MockitoJUnitRunner])
class DocumentsLoaderTest {

  @InjectMocks
  var documentsLoader: DocumentsLoader = _

  @Mock
  var documentsClient: DocumentsClient = _

  @Before
  def init(): Unit = {

  }

  @Test
  def uploadsAllDocuments() {
    // Uses JUnit-style assertions
    val filePath = classOf[DocumentsLoaderTest].getResource("/resources-root").getPath
    val ix = filePath.lastIndexOf("/")
    val resourceFolder = filePath.substring(0, ix) + "/documents/xsd"

    documentsLoader.loadDocuments(resourceFolder, "xsd")

    Mockito.verify(documentsClient, Mockito.times(1)).post(Matchers.any())
  }
}
