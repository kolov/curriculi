package curri.app

import curri.CurriculiApp
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer
import org.springframework.stereotype.Component

/**
  * Created by assen on 24/12/2016.
  */
@Component
class Initializer extends AbstractHttpSessionApplicationInitializer(classOf[CurriculiApp]) {

}
