package example

import org.scalatest.funspec.AnyFunSpec
import cats.data.Writer
import cats.syntax.applicative._
import cats.syntax.writer._
import cats.instances.vector._
import scala.concurrent._
import scala.concurrent.duration._
import ExecutionContext.Implicits.global

// Writer monad ex
class ShowYourWorking extends AnyFunSpec {

  def slowly[A](body: => A) =
    try body
    finally Thread.sleep(100)

  type Logged[A] = Writer[Vector[String], A]

  def factorial(n: Int): Writer[Vector[String], Int] = {
    val ans = slowly(
      if (n == 0) 1.pure[Logged] else factorial(n - 1).map(_ * n)
    )
    ans.flatMap((res) => (res).writer(Vector(s"fact $n ${ans.value}")))
  }

  it("runs in parallel and produces writers") {
    val result = Await.result(
      Future.sequence(
        Vector(
          Future(factorial(5)),
          Future(factorial(5))
        )
      ),
      5.seconds
    )

    assert(result.length == 2)

    println(result(0))
    for (curr <- result) {
      assert(curr.value == 120)
      assert(curr.written.length == 6)
    }
  }

}
