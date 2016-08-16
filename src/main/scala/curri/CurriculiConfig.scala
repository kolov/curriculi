package curri

import curri.web.CookieFilter
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.context.embedded.FilterRegistrationBean
import org.springframework.context.annotation.{ComponentScan, Configuration}

@Configuration
@EnableAutoConfiguration
@ComponentScan
class CurriculiConfig {


  //@Bean
  def someFilterRegistration(cookieFilter: CookieFilter) {

    val registration = new FilterRegistrationBean()
    registration.setFilter(cookieFilter);
    registration.addUrlPatterns("/url/*");
    registration.addInitParameter("paramName", "paramValue");
    registration.setName("someFilter");
    registration.setOrder(1);
    return registration;
  }

  @Value("${curri.properties.read}")
  var propertiesRead: String = ""
}