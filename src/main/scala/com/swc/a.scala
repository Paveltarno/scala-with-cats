package com.swc

import cats.MonadError
import cats.instances.either._
import cats.Eval

import cats.implicits._
import cats.data.EitherT
import scala.concurrent.Future
import cats.data.Writer

object Zoo {

  // Start with a default nothing
  val result = for {
    _ <- "".writer(Vector("Empty string created"))
    firstName <- "pavel".writer(Vector("First name created"))
    lastName <- "tarno".writer(Vector("First name created"))
  } yield s"$firstName $lastName"

  println(s"result is: ${result.value}") // result is: pavel tarno
  println(s"log is: ${result.written}") // log is: Vector(Empty string created, First name created, First name created)

}
