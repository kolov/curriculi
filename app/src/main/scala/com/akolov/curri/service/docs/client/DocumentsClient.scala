package com.akolov.curri.service.docs.client

import com.akolov.curri.service.docs.domain.Document
import com.akolov.curri.web.app.FeignConfig
import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod._


@FeignClient(name = "service-users",
  configuration = Array(classOf[FeignConfig])
)
trait DocumentsClient {
  @RequestMapping(method = Array(GET), value = Array("/"))
  def findByProviderCodeAndRemoteId( ): ResponseEntity[Document]


}

