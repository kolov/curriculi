package curri

import org.springframework.boot.SpringApplication

object CurriculiApp extends App {

  SpringApplication.run(classOf[CurriculiConfig], "--debug");

}