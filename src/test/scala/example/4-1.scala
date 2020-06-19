package example

import org.scalatest.funspec.AnyFunSpec

class GettingFuncySpec extends AnyFunSpec {

  trait Monad[F[_]] {
    def pure[A](in: A): F[A]
    def flatMap[A, B](source: F[A])(fn: A => F[B]): F[B]

    // Implement this
    def map[A, B](source: F[A])(fn: A => B): F[B] =
      flatMap(source)((q) => pure(fn(q)))

  }

}
