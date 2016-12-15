package curri.domain

import com.fasterxml.jackson.core.`type`.TypeReference

object IdentityProvider extends Enumeration {
  type IdentityProvider = Value
  val FACEBOOK = Value("FACEBOOK")
  val GOOGLE = Value("GOOGLE")
}

class IdentityProviderType extends TypeReference[IdentityProvider.type]