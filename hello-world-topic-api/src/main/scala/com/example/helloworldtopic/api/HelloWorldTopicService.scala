package com.example.helloworldtopic.api

import com.lightbend.lagom.scaladsl.api.{Descriptor, Service}

trait HelloWorldTopicService extends Service {


    override final def descriptor: Descriptor = {
      import Service._

      named("hello-world-topic")
        .withCalls()
        .withTopics()
        .withAutoAcl(true)
    }
}
