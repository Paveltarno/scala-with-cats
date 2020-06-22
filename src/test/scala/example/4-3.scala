package example

import org.scalatest.funspec._
import cats.Id

class MonadicSecretIdentities extends AnyFunSpec {
  def pure[A](in: A): Id[A] = in
  def map[A, B](in: Id[A])(f: A => B) = f(in)
  def flatMap[A, B](in: Id[A])(f: A => Id[B]) = f(in)

  it("has a pure method") {
    assert(pure(1) == 1)
  }

  it("has a map method") {
    assert(map(1)(_.toString()) == "1")
  }

  it("has a flatmap method") {
    assert(flatMap(1)(_.toString()) == "1")
  }
}
