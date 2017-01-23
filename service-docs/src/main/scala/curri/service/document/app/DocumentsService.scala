package curri

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.SpringApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class DocumentsServiceApp {

}

object DocumentsServiceApp extends App {
  SpringApplication.run(classOf[DocumentsServiceApp]);
}