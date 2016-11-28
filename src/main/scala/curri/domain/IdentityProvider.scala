package curri.domain

import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.module.scala.JsonScalaEnumeration
import curri.domain.IdentityProvider.IdentityProvider

/**
  * Created by assen on 27/11/2016.
  */
object IdentityProvider extends Enumeration {
  type IdentityProvider = Value
  val FACEBOOK = Value("FACEBOOK")
  val GOOGLE = Value("GOOGLE")

}

class IdentityProviderType extends TypeReference[IdentityProvider.type]