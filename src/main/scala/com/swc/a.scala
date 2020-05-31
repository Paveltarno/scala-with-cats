package com.swc

import cats.Functor
import cats.instances.option._
import cats.instances.int._

object Foo {

  val intToString: Int => String = _.toString()

  val qq = Functor[Option].map(Some(1))(intToString) // Result is Option[String]
  val qe = Functor[Option].lift(intToString)(Some(1)) // Result is Option[String]
}
