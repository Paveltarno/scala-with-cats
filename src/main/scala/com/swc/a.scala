package com.swc

import cats.Functor
import cats.instances.option._
import cats.instances.int._

object Zoo {

  val q = Option(2)

  class Foo[T](val in: T) {
    def flatMap(fn: (T => Foo[T])): Foo[T] = fn(in)
    def map(fn: (T => T)): Foo[T] = new Foo(fn(in))
  }
  object Foo {
    def apply[T](in: T) = new Foo(in)
  }

  val bf = Foo(2)

  val z = for {
    a <- Foo(1)
    b <- Foo(2)
  } yield a / b

  val zz = (Foo(1).flatMap((a) => Foo(2).map(b => a / b)))

}
