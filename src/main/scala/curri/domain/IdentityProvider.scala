package curri.domain

import com.fasterxml.jackson.core.`type`.TypeReference

/**
  * Created by assen on 27/11/2016.
  */
object IdentityProvider extends Enumeration {
  type IdentityProvider = Value
  val FACEBOOK = Value("FACEBOOK")
  val GOOGLE = Value("GOOGLE")

}

class IdentityProviderType extends TypeReference[IdentityProvider.type]