package example

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.funspec.AnyFunSpec

// 2.3 Exercise: The Truth About Monoids

trait Semigroup[A] {
  def combine(a: A, b: A): A
}

trait Monoid[A] extends Semigroup[A] {
  def empty: A
}

object Monoid {
  def apply[A](implicit m: Monoid[A]) = m
}

class Spec extends AnyFunSpec with Matchers {

  implicit val boolOrMonoid: Monoid[Boolean] = new Monoid[Boolean] {

    override def combine(a: Boolean, b: Boolean): Boolean =
      a || b

    override def empty: Boolean = false

  }

  implicit val boolAndMonoid: Monoid[Boolean] = new Monoid[Boolean] {

    override def combine(a: Boolean, b: Boolean): Boolean =
      a && b

    override def empty: Boolean = false

  }

  implicit val boolXorMonoid: Monoid[Boolean] = new Monoid[Boolean] {

    override def combine(a: Boolean, b: Boolean): Boolean =
      (a != b)

    override def empty: Boolean = false

  }

  def testReferential(monoid: Monoid[Boolean]) = {
    monoid.combine(
      false,
      boolOrMonoid.combine(true, false)
    ) === boolOrMonoid.combine(
      boolOrMonoid.combine(false, true),
      false
    )
  }

  def testIdentity(monoid: Monoid[Boolean]) = {
    monoid.combine(
      true,
      false
    ) === monoid.combine(
      monoid.combine(true, false),
      false
    )
  }

  describe("MonoidExercises") {
    describe("boolOrMonoid") {
      it("should be referential") {
        testReferential(boolOrMonoid)
      }
      it("should have an identity") {
        testIdentity(boolOrMonoid)
      }
    }

    describe("boolAndMonoid") {
      it("should be referential") {
        testReferential(boolAndMonoid)
      }
      it("should have an identity") {
        testIdentity(boolAndMonoid)
      }
    }

    describe("boolXorMonoid") {
      it("should be referential") {
        testReferential(boolXorMonoid)
      }
      it("should have an identity") {
        testIdentity(boolXorMonoid)
      }
    }
  }

}
