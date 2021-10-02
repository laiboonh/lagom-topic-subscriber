package com.example.helloworldtopic.impl.subscriber

import akka.Done
import akka.stream.scaladsl.Flow
import com.example.helloworld.api.{GreetingMessageChanged, HelloWorldService}

class HelloWorldSubscriber(helloWorldService: HelloWorldService) {
    helloWorldService
      .greetingsTopic()
      .subscribe // <-- you get back a Subscriber instance
      .atLeastOnce(
        Flow.fromFunction(doSomethingWithTheMessage)
      )

  def doSomethingWithTheMessage(msg: GreetingMessageChanged): Done = {
    println(msg)
    Done
  }
}
