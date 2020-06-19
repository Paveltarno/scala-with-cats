package com.swc

import cats.Functor
import cats.Applicative
import cats.Id
import cats.Monad
import cats.implicits._

object Zoo {

  val in: Id[Int] = 1
  val out = in.flatMap(_ + 1) 

}
