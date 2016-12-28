package curri.oauth

import curri.service.user.domain.Identity
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.stereotype.Component

/**
  * Created by assen on 16/12/2016.
  */
@Component
class GoogleProvider extends Provider {
  override def canHandle(oauth: OAuth2Authentication): Boolean = {
    val details = oauth.getUserAuthentication.getDetails.asInstanceOf[java.util.Map[String, Object]]
    val profile = details.get("profile").asInstanceOf[String]

    profile != null && profile.contains("google.com")
  }

  override def createIdentity(oauth: OAuth2Authentication): Identity = {
    val details = oauth.getUserAuthentication.getDetails.asInstanceOf[java.util.Map[String, Object]]

    val identity = new Identity()
    identity.setProviderCode(Codes.GOOGLE.toString)
    identity.setFirstName(details.get("given_name").asInstanceOf[String])
    identity.setLastName(details.get("family_name").asInstanceOf[String])
    identity.setName(details.get("name").asInstanceOf[String])
    identity.setPicture(details.get("picture").asInstanceOf[String])
    identity.setGender(details.get("gender").asInstanceOf[String])
    identity.setLink(details.get("profile").asInstanceOf[String])
    identity.setRemoteId(details.get("sub").asInstanceOf[String])

    identity
  }
}
