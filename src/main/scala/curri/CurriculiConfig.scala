package curri

import curri.web.CookieFilter
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}

@Configuration
@EnableAutoConfiguration
@ComponentScan
class CurriculiConfig {


  @Bean
  def someFilterRegistration(cookieFilter: CookieFilter): FilterRegistrationBean = {

    val registration = new FilterRegistrationBean()
    registration.setFilter(cookieFilter);
    registration.addUrlPatterns("/v1.0/*");
    //registration.addInitParameter("paramName", "paramValue");
    registration.setName("cookieFilter");
    registration.setOrder(1);
    return registration;
  }

}