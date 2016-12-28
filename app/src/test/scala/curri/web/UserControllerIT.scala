package curri.web

import curri.service.user.persist.{IdentityRepository, UserRepository}
import curri.service.user.web.UserController
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.PropertySource
import org.springframework.http.MediaType
import org.springframework.test.context.{ContextConfiguration, TestPropertySource}
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@RunWith(classOf[SpringRunner])
@WebMvcTest(Array(classOf[UserController]))
@ContextConfiguration
@WebAppConfiguration
class UserControllerIT {

  @Autowired
  var mvc: MockMvc = _

  @MockBean
  var userService: UserRepository = _

  @MockBean
  var identityRepository: IdentityRepository = _

  @Test
  def exampleTest(): Unit = {
    mvc.perform(MockMvcRequestBuilders.get("/user").accept(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isOk());
  }
}
