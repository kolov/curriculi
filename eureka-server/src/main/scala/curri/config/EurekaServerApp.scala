package curri.config

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@SpringBootApplication
@EnableEurekaServer
class EurekaServerApp {

}

object EurekaServerApp {

  def main(args: Array[String]) {
    SpringApplication.run(classOf[EurekaServerApp])
  }
}