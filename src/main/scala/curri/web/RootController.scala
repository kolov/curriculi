package curri.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class RootController {

  @RequestMapping(Array("/"))
  def handleRootRequest(): String = "redirect:/index"

  @RequestMapping(Array("/index"))
  def handleIndex(): String = "/index"
}
