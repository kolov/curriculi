package com.akolov.curri.web.app

import curri.NotFoundException
import feign.Logger
import feign.codec.ErrorDecoder
import org.springframework.context.annotation.{Bean, Configuration}

/**
  * Created by assen on 29/01/2017.
  */
@Configuration
class FeignConfig {

  @Bean
  def errorDecoder = new ErrorDecoder {

    val defaultErrorDecoder: ErrorDecoder = new ErrorDecoder.Default();

    @Override
    def decode(methodKey: String, response: feign.Response): Exception = {
      if (response.status() == 404) {
        return new NotFoundException("no such identity");
      }
      return defaultErrorDecoder.decode(methodKey, response);
    }
  }

  @Bean
  def feignLoggerLevel() : Logger.Level = Logger.Level.FULL

}
