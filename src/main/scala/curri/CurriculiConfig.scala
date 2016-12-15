package curri

import java.lang.Exception
import java.util
import javax.servlet.Filter

import com.fasterxml.jackson.module.scala.DefaultScalaModule
import curri.web.CookieFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.security.oauth2.resource.{ResourceServerProperties, UserInfoTokenServices}
import org.springframework.boot.autoconfigure.{EnableAutoConfiguration, SpringBootApplication}
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}
import org.springframework.data.mongodb.MongoDbFactory
import org.springframework.data.mongodb.core.convert.{CustomConversions, DefaultDbRefResolver, MappingMongoConverter}
import org.springframework.data.mongodb.core.mapping.MongoMappingContext
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.{EnableWebSecurity, WebSecurityConfigurerAdapter}
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails
import org.springframework.security.oauth2.client.{OAuth2ClientContext, OAuth2RestTemplate}
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.csrf.CookieCsrfTokenRepository

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableOAuth2Client
@EnableWebSecurity
@SpringBootApplication
class CurriculiConfig extends WebSecurityConfigurerAdapter {


  @Autowired
  var oauth2ClientContext: OAuth2ClientContext = _

  override def configure(http: HttpSecurity): Unit = {

    http
      .logout().logoutSuccessUrl("/").permitAll()
      .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

      .and()
      .antMatcher("/**")
      .addFilterBefore(ssoFilter, classOf[BasicAuthenticationFilter])
      .authorizeRequests()
      .antMatchers("/login/*")
      .authenticated()
      .anyRequest()
      .permitAll()

  }

  def ssoFilter: Filter = {
    val facebookFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/facebook")
    val facebookTemplate = new OAuth2RestTemplate(facebook(), oauth2ClientContext);
    facebookFilter.setRestTemplate(facebookTemplate);
    facebookFilter.setTokenServices(
      new UserInfoTokenServices(facebookResource().getUserInfoUri(),
        facebook().getClientId()));
    return facebookFilter;
  }

  @Bean
  @ConfigurationProperties("facebook.client")
  def facebook(): AuthorizationCodeResourceDetails =
    new AuthorizationCodeResourceDetails();


  @Bean
  @ConfigurationProperties("facebook.resource")
  def facebookResource(): ResourceServerProperties =
    new ResourceServerProperties();


  @Bean
  def someFilterRegistration(cookieFilter: CookieFilter): FilterRegistrationBean = {
    val registration = new FilterRegistrationBean()
    registration.setFilter(cookieFilter);
    registration.addUrlPatterns("/*");
    registration.setName("cookieFilter");
    // order is lowest precedence by default
    return registration;
  }

  @Autowired
  var mongoFactory: MongoDbFactory = _

  @Autowired
  var mongoMappingContext: MongoMappingContext = _



  @Bean
  def mongoConverter(): MappingMongoConverter = {
    val dbRefResolver = new DefaultDbRefResolver(mongoFactory)
    val mongoConverter = new MappingMongoConverter(dbRefResolver, mongoMappingContext) {

    }
    // this is my customization
    mongoConverter.setMapKeyDotReplacement("_");
    val converters = new java.util.ArrayList()
    val conversions = new CustomConversions(converters)

    mongoConverter.setCustomConversions(conversions)
    mongoConverter.afterPropertiesSet();

    mongoConverter
  }

  @Bean
  def scalaMapper = DefaultScalaModule
}

object CurriculiConfig extends App {

  SpringApplication.run(classOf[CurriculiConfig]);

}