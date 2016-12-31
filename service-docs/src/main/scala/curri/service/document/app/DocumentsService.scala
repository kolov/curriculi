package curri

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.SpringApplication

@SpringBootApplication
class DocumentsServiceApp {

}

object DocumentsServiceApp extends App {
  SpringApplication.run(classOf[DocumentsServiceApp]);
}