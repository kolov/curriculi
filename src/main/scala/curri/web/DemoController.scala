package curri.web

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.core.env.PropertyResolver
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
  *
  */

@Controller
@RequestMapping(Array("/demo"))
class DemoController() {

  val LOG = LoggerFactory.getLogger(getClass)

  val USER: String = "USER"

  @Value("${foo:'Not Found'}")
  var foo: String = ""

  @RequestMapping(method = Array(RequestMethod.GET))
  @ResponseBody
  def showFoo(): String = "foo=" + foo + "\n"

  @RequestMapping(value = Array("/required"), method = Array(RequestMethod.GET))
  @ResponseBody
  def resolve(): String = propertyResolver.getRequiredProperty("foo")


  @Autowired
  var propertyResolver: PropertyResolver = null

}