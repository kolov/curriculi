package curri.tools

import org.springframework.boot.{ApplicationArguments, ApplicationRunner, SpringApplication}
import org.springframework.boot.autoconfigure.{EnableAutoConfiguration, SpringBootApplication}
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}


@SpringBootApplication
class LoadInitialDocuments {

  @Bean
  def applicationRunner = new ApplicationRunner {
    override def run(args: ApplicationArguments): Unit = {

    }
  }
}

object LoadInitialDocuments extends App {

  SpringApplication.run(classOf[LoadInitialDocuments]);

}