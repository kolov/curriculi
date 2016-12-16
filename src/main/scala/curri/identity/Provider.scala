package curri.identity

import curri.domain.Identity
import org.springframework.security.oauth2.provider.OAuth2Authentication

/**
  * Created by assen on 16/12/2016.
  */
trait Provider {

  def canHandle(auth: OAuth2Authentication) : Boolean

  def createIdentity(auth: OAuth2Authentication) : Identity

}
