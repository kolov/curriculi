package curri.web

import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.junit4.SpringRunner


@RunWith(classOf[SpringRunner])
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UserControllerTest {

  @Autowired
  var restTemplate: TestRestTemplate = _

  @Test
  def exampleTest() :Unit = {
    val body = restTemplate.getForObject("/user", classOf[String]);
    Assertions.assertThat(body).isEqualTo("Hello World");
  }
}
