package example

import com.swc.Monoid
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.funspec.AnyFunSpec

// 2.3 Exercise: The Truth About Monoids



class MonoidExerciseBooleanSpec extends AnyFunSpec with Matchers {

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
      monoid.combine(true, false)
    ) === monoid.combine(
      monoid.combine(false, true),
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

  describe("MonoidExerciseBoolean") {
    describe("boolOrMonoid") {
      it("should be referential") {
        assert(testReferential(boolOrMonoid))
      }
      it("should have an identity") {
        assert(testIdentity(boolOrMonoid))
      }
    }

    describe("boolAndMonoid") {
      it("should be referential") {
        assert(testReferential(boolAndMonoid))
      }
      it("should have an identity") {
        assert(testIdentity(boolAndMonoid))
      }
    }

    describe("boolXorMonoid") {
      it("should be referential") {
        assert(testReferential(boolXorMonoid))
      }
      it("should have an identity") {
        assert(testIdentity(boolXorMonoid))
      }
    }
  }

}
