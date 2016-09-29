package curri

import org.springframework.boot.SpringApplication
//import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso

//@EnableOAuth2Sso
object CurriculiApp extends App {

  SpringApplication.run(classOf[CurriculiConfig], "--debug");

}