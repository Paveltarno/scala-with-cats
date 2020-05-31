import cats.instances.int._
import cats.instances.option._
import cats.instances.double._
import cats.syntax.semigroup._
import cats.Monoid

import org.scalatest.funspec.AnyFunSpec

case class Order(totalCost: Double, quantity: Double)

object SuperAdder {

  def add[T: Monoid](items: List[T]): T = {
    items.foldLeft(Monoid[T].empty)(_ |+| _)
  }
}

class SuperAdderSpec extends AnyFunSpec {
  implicit val orderMonoid = new Monoid[Order] {

    override def combine(x: Order, y: Order): Order =
      Order(x.totalCost |+| y.totalCost, x.quantity |+| y.quantity)

    override def empty: Order = Order(0, 0)

  }

  describe("SuperAdder") {

    it("should combine Int items") {
      assert(SuperAdder.add(List(1, 5, 7)) == 13)
    }

    it("should combine Option[Int] items") {
      assert(SuperAdder.add(List(Some(5), Some(10), None)) == Some(15))
    }

    it("should combine Order items") {
      assert(
        SuperAdder
          .add(List(Order(0, 0), Order(5, 5), Order(10, 10))) == Order(15, 15)
      )
    }

  }
}
