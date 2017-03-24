package com.akolov.curri.web.oauth

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import scala.beans.BeanProperty
import scala.collection.JavaConversions

/**
  * Created by assen on 16/12/2016.
  */
@Component
class AllProviders {

  @BeanProperty
  var providers: List[Provider] = _

  @Autowired
  def setProfidersList(p: java.util.List[Provider]) = {
    providers = JavaConversions.asScalaBuffer(p).toList
  }

}
