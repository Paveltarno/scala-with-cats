package example

import com.swc.Monoid
import com.swc.Semigroup
import org.scalatest.funspec.AnyFunSpec

// 2.4 Exercise: Exercise: All Set for Monoids

class MonoidExerciseSetSpec extends AnyFunSpec {

  implicit def setUnionMonoid[A]: Monoid[Set[A]] = new Monoid[Set[A]] {

    override def combine(a: Set[A], b: Set[A]): Set[A] = a union b

    override def empty: Set[A] = Set.empty[A]

  }

  implicit def setIntersectionSemigroup[A]: Semigroup[Set[A]] =
    new Semigroup[Set[A]] {

      override def combine(a: Set[A], b: Set[A]): Set[A] = a intersect b

    }

  def testReferential(sg: Semigroup[Set[Int]]) = {
    sg.combine(
      Set(1, 2, 3),
      sg.combine(
        Set(2, 3, 4),
        Set(3, 4, 5)
      )
    ) equals sg.combine(
      sg.combine(
        Set(1, 2, 3),
        Set(2, 3, 4)
      ),
      Set(3, 4, 5)
    )
  }

  def testIdentity(monoid: Monoid[Set[Int]]) = {
    monoid.combine(
      Set(1, 2, 3),
      Set(2, 3, 4)
    ) equals monoid.combine(
      Set(1, 2, 3),
      monoid.combine(
        Set(2, 3, 4),
        Set.empty
      )
    )
  }

  val targets = Map(
    "setUnionMonoid" -> setUnionMonoid[Int],
    "setIntersectionSemigroup" -> setIntersectionSemigroup[Int]
  )
  describe("MonoidExerciseSet") {
    targets.foreachEntry((name, target) => {
      describe(name) {
        it("should be referential") {
          assert(testReferential(target))
        }
        target match {
          case monoid: Monoid[_] => assert(testIdentity(monoid))
          case _ => ()
        }
      }
    })

  }

}
