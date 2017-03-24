package com.akolov.curri.web

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.core.env.PropertyResolver
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{PathVariable, RequestMapping, RequestMethod, ResponseBody}

/**
  * Stuff to test
  */

@Controller
@RequestMapping(Array("/demo"))
class DemoController() {

  val LOG = LoggerFactory.getLogger(getClass)

  val USER: String = "USER"

  @Value("${foo:'Not Found'}")
  var foo: String = _
  //
  //  @Value("${oauth2.google.client.clientSecret}")
  //  var clientSecret: String = _

  @RequestMapping(method = Array(RequestMethod.GET))
  @ResponseBody
  def showFoo(): String = "foo=" + foo + "\n"

  @RequestMapping(value = Array("/required"), method = Array(RequestMethod.GET))
  @ResponseBody
  def resolve(): String = propertyResolver.getRequiredProperty("foo")


  @Autowired
  var propertyResolver: PropertyResolver = null


  @Autowired
  var discoveryClient: DiscoveryClient = null

  @RequestMapping(Array("/service-instances/{applicationName}"))
  @ResponseBody
  def serviceInstancesByApplicationName(@PathVariable applicationName: String) =
    discoveryClient.getInstances(applicationName);

  @RequestMapping(Array("/services"))
  @ResponseBody
  def services() =
    discoveryClient.getServices


}