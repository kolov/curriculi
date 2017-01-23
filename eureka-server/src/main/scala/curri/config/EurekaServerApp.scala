package curri.config

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.config.server.EnableConfigServer

@SpringBootApplication
@EnableEurekaServer
class EurekaServerApp {

}

object EurekaServerApp {

  def main(args: Array[String]) {
    SpringApplication.run(classOf[ConfigServerApp])
  }
}