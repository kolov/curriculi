package curri

import javax.servlet.Filter

import com.fasterxml.jackson.module.scala.DefaultScalaModule
import curri.web.CookieFilter
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.security.oauth2.resource.{ResourceServerProperties, UserInfoTokenServices}
import org.springframework.boot.autoconfigure.{EnableAutoConfiguration, SpringBootApplication}
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation._
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.{EnableWebSecurity, WebSecurityConfigurerAdapter}
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails
import org.springframework.security.oauth2.client.{OAuth2ClientContext, OAuth2RestTemplate}
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession


@EnableOAuth2Client
@EnableWebSecurity
@SpringBootApplication
@EnableRedisHttpSession
class CurriculiApp extends WebSecurityConfigurerAdapter {

  @Autowired
  var oauth2ClientContext: OAuth2ClientContext = _

  override def configure(http: HttpSecurity): Unit = {

    http
      .logout().logoutSuccessUrl("/").permitAll()
      .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
      .and()
      .antMatcher("/**")
      .addFilterBefore(ssoGoogleFilter, classOf[BasicAuthenticationFilter])
      .addFilterBefore(ssoFacebookFilter, classOf[BasicAuthenticationFilter])
      .authorizeRequests()
      .antMatchers("/login/*")
      .authenticated()
      .anyRequest()
      .permitAll()

  }

  class FacebookUserInfoTokenServices(userInfoEndpointUrl: String, clientId: String)
    extends UserInfoTokenServices(userInfoEndpointUrl, clientId) {


    override def loadAuthentication(accessToken: String): OAuth2Authentication = {
      val auth = super.loadAuthentication(accessToken)
      auth.getUserAuthentication.getDetails.asInstanceOf[java.util.Map[String, String]].put("oauthProvider", "facebook")
      auth
    }
  }

  def ssoFacebookFilter: Filter = {
    val facebookFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/facebook")
    val facebookTemplate = new OAuth2RestTemplate(facebook(), oauth2ClientContext);
    facebookFilter.setRestTemplate(facebookTemplate);
    facebookFilter.setTokenServices(
      new FacebookUserInfoTokenServices(facebookResource().getUserInfoUri(),
        facebook().getClientId()));
    return facebookFilter;
  }

  def ssoGoogleFilter: Filter = {
    val googleFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/google")
    val googleTemplate = new OAuth2RestTemplate(google(), oauth2ClientContext);
    googleFilter.setRestTemplate(googleTemplate);
    googleFilter.setTokenServices(
      new UserInfoTokenServices(googleResource().getUserInfoUri(),
        google().getClientId()));
    return googleFilter;
  }

  @Bean
  @ConfigurationProperties("oauth2.facebook.client")
  def facebook(): AuthorizationCodeResourceDetails =
    new AuthorizationCodeResourceDetails();


  @Bean
  @ConfigurationProperties("oauth2.facebook.resource")
  def facebookResource(): ResourceServerProperties =
    new ResourceServerProperties();


  @Bean
  @ConfigurationProperties("oauth2.google.client")
  def google(): AuthorizationCodeResourceDetails =
    new AuthorizationCodeResourceDetails();


  @Bean
  @ConfigurationProperties("oauth2.google.resource")
  def googleResource(): ResourceServerProperties =
    new ResourceServerProperties();


  @Bean
  def cookieFilterRegistration(cookieFilter: CookieFilter): FilterRegistrationBean = {
    val registration = new FilterRegistrationBean()
    registration.setFilter(cookieFilter);
    registration.addUrlPatterns("/v1/*", "/login/*");
    registration.setName("cookieFilter");
    // order is lowest precedence by default
    return registration;
  }


  @Bean
  def scalaMapper = DefaultScalaModule

  @Value("${spring.data.redis.host:localhost}")
  private var redisHost: String = _

  @Bean
  def connectionFactoryDocker = new LettuceConnectionFactory(redisHost, 6379);

}

object CurriculiApp extends App {

  SpringApplication.run(classOf[CurriculiApp]);

}