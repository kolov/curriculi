package curri.config

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.config.server.EnableConfigServer

@SpringBootApplication
@EnableConfigServer
class ConfigServerApp {

}

object ConfigServerApp {


  def main(args: Array[String]) {
    val is = ConfigServerApp.getClass().getResourceAsStream("/application.yml")

    val buf = Stream.continually(is.read).takeWhile(_ != -1).map(_.toByte).toArray
    println(new String(buf))
    SpringApplication.run(classOf[ConfigServerApp])
  }
}