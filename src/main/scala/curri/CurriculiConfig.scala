package curri

import javax.servlet.Filter

import curri.web.CookieFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.security.oauth2.resource.{ResourceServerProperties, UserInfoTokenServices}
import org.springframework.boot.autoconfigure.{EnableAutoConfiguration, SpringBootApplication}
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
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
    registration.setOrder(1);
    return registration;
  }

}

object CurriculiConfig extends App {

    SpringApplication.run(classOf[CurriculiConfig]);

}