package curri.identity

import curri.domain.Identity
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.stereotype.Component

/**
  * Created by assen on 16/12/2016.
  */
@Component
class FacebookProvider extends Provider {
  override def canHandle(oauth: OAuth2Authentication): Boolean = {
    val details = oauth.getUserAuthentication.getDetails.asInstanceOf[java.util.Map[String, Object]]
    val link = details.get("oauthProvider").asInstanceOf[String]

    link != null && link.contains("facebook")
  }

  override def createIdentity(oauth: OAuth2Authentication): Identity = {
    val details = oauth.getUserAuthentication.getDetails.asInstanceOf[java.util.Map[String, Object]]

    val identity = new Identity()
    identity.setProviderCode(Codes.FACEBOOK.toString)
    identity.setFirstName(details.get("first_name").asInstanceOf[String])
    identity.setLastName(details.get("last_name").asInstanceOf[String])
    identity.setName(details.get("name").asInstanceOf[String])
    identity.setLink(details.get("link").asInstanceOf[String])
    identity.setGender(details.get("gender").asInstanceOf[String])
    identity.setRemoteId(details.get("id").asInstanceOf[String])

    identity
  }
}
