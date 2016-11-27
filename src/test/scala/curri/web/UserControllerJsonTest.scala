package curri.web

import com.fasterxml.jackson.databind.ObjectMapper
import curri.domain.IdentityProvider._
import curri.domain.{Identity, IdentityProvider, User}
import curri.service.UserRepository
import org.junit.Before
import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.util.ReflectionTestUtils

@RunWith(classOf[SpringRunner])
@JsonTest
//@Import(Array( classOf[WebSecurityConfiguration],
//  classOf[AuthenticationConfiguration],
//  classOf[ObjectPostProcessorConfiguration]))
@SpringBootTest
//@EnableWebSecurity
//@WebAppConfiguration
class UserControllerJsonTest {

  var json: JacksonTester[curri.domain.User] = _


  @Before
  def setup(): Unit = {
    JacksonTester.initFields(this, new ObjectMapper())
  }


  @MockBean
  var userRepo: UserRepository = _


  @Test
  def exampleTest(): Unit = {

    val user: User = new User()
    val identity = new Identity()
    identity.setIdentityProvider(FACEBOOK)
    identity.setFirstName("John")
    identity.setLastName("Doe")
    identity.setProviderId("12345")

    ReflectionTestUtils.setField(user, "cookieValue", "abcde")
    ReflectionTestUtils.setField(user, "identity", identity)

    Assertions.assertThat(json.write(user).getJson).isEqualTo("xx")
    Assertions.assertThat(json.write(user)) .isEqualToJson("user-1-test.json")

  }
}
