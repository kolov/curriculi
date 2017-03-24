package curri.tools

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.stereotype.Component




@FeignClient(name = "service-users",
  configuration = Array(classOf[FeignConfig])
  // , fallback = classOf[UsersClientCallback]
)
trait DocumentsClient {


}

@Component
class DocumentsServiceClient @Autowired()(private val client: DocumentsClient) {

}

