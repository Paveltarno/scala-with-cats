package example

import org.scalatest.funspec.AnyFunSpec
import cats.Eval

class SaferFoldingUsingEval extends AnyFunSpec {
  
  def foldRight[A, B](as: List[A], acc: B)(fn: (A, B) => B): B = as match {
    case head :: tail =>
      fn(head, foldRight(tail, acc)(fn))
    case Nil =>
      acc
  }

  def foldRightEval[A, B](as: List[A], acc: Eval[B])(
      fn: (A, Eval[B]) => Eval[B]
  ): Eval[B] = {
    as match {
      case head :: tail =>
        Eval.defer(fn(head, foldRight(tail, acc)(fn)))
      case Nil =>
        acc
    }
  }

  it("the original foldRight it not stack safe") {
    assertThrows[StackOverflowError] {
      val l = (0 to 1000000).toList
      val result = foldRight(l, 0)((i, acc) => {
        acc + i
      })
    }
  }

  it("the new foldRight is stack safe") {
    val l = (0 to 10).toList
    val result = foldRightEval(l, Eval.now(0))((i, acc) => {
      Eval.now(acc.value + i)
    })
  }

}
