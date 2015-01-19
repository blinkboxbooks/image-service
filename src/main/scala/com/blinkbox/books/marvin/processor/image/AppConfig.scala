package com.blinkbox.books.marvin.processor.image

import java.util.concurrent.TimeUnit

import com.blinkbox.books.rabbitmq.RabbitMqConfig
import com.blinkbox.books.rabbitmq.RabbitMqConfirmedPublisher.PublisherConfiguration
import com.blinkbox.books.rabbitmq.RabbitMqConsumer.QueueConfiguration
import com.typesafe.config.Config

import scala.concurrent.duration.{FiniteDuration, _}

case class AppConfig(rabbitmq: RabbitMqConfig, retryInterval: FiniteDuration, actorTimeout: FiniteDuration,
  input: QueueConfiguration, output: PublisherConfiguration, error: PublisherConfiguration)

object AppConfig {
  val prefix = "service.imageProcessor"
  def apply(config: Config): AppConfig = AppConfig(
    RabbitMqConfig(config.getConfig(s"$prefix")),
    config.getDuration(s"$prefix.retryInterval", TimeUnit.SECONDS).seconds,
    config.getDuration(s"$prefix.actorTimeout", TimeUnit.SECONDS).seconds,
    QueueConfiguration(config.getConfig(s"$prefix.input")),
    PublisherConfiguration(config.getConfig(s"$prefix.output")),
    PublisherConfiguration(config.getConfig(s"$prefix.error"))
  )
}