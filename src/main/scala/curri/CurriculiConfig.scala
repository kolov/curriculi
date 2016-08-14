package curri

import javax.servlet.Filter

import curri.web.CookieFilter
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.context.embedded.FilterRegistrationBean

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
}