package com.swc

import cats.Monoid
import cats.instances.int._ // for Monoid 
import cats.instances.option._ // for Monoid
import scala.concurrent.Future
import scala.concurrent.{Future, Await}
import scala.concurrent.ExecutionContext.Implicits.global

object yet{

  val future: Future[String] = Future(123).
map(n => n + 1). map(n => n * 2). map(n => s"${n}!")
Await.result(future, 1.second)

}

