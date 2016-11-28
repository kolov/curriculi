package curri.web

import com.fasterxml.jackson.databind.ObjectMapper
import curri.domain.IdentityProvider._
import curri.domain.{Identity, User}
import curri.service.UserRepository
import org.assertj.core.api.Assertions
import org.junit.runner.RunWith
import org.junit.{Before, Test}
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.util.ReflectionTestUtils

@RunWith(classOf[SpringRunner])
@JsonTest
@SpringBootTest
class UserControllerJsonTest {

  var json: JacksonTester[curri.domain.User] = _

  @Before
  def setup(): Unit = {
    JacksonTester.initFields(this, new ObjectMapper())
  }


  //normally created by spring-data, needed to initialize context
  @MockBean
  var userRepo: UserRepository = _


  @Test
  def exampleTest(): Unit = {

    val user: User = new User()
    val identity = new Identity()
    identity.setProvider(FACEBOOK.toString)
    identity.setFirstName("John")
    identity.setLastName("Doe")
    identity.setProviderId("12345")

    ReflectionTestUtils.setField(user, "cookieValue", "abcde")
    ReflectionTestUtils.setField(user, "identity", identity)

    Assertions.assertThat(json.write(user)).isEqualToJson("user-1-test.json")

  }
}
