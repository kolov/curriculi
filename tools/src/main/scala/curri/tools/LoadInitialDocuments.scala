package curri.tools

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.{EnableAutoConfiguration, SpringBootApplication}
import org.springframework.context.annotation.{ComponentScan, Configuration}


@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
class LoadInitialDocuments {

}

object LoadInitialDocuments extends App {

  SpringApplication.run(classOf[LoadInitialDocuments]);

}