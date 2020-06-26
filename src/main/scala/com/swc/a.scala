package com.swc

import cats.MonadError
import cats.instances.either._
import cats.Eval

object Zoo {
  def factorial(n: BigInt): Eval[BigInt] =
    if (n == 1) {
      Eval.now(n)
    } else {
      factorial(n - 1).map(_ * n)
    }
    
}
