package com.akolov.curri.service.docs.domain

import com.fasterxml.jackson.annotation.JsonProperty


case class Document( @JsonProperty("title") title: String,
  @JsonProperty("kind") kind: String,
  @JsonProperty("body") body: String,
  @JsonProperty("ownerUser") ownerUser: Option[String],
  @JsonProperty("ownerGroup") ownerGroup: Option[String])