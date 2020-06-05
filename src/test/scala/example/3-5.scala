package example

import org.scalatest.funspec.AnyFunSpec
import cats.Functor
import cats.syntax.functor._

// Write a Functor for the following binary tree data type.
// Verify that the code works as expected on instances of Branch and Leaf

class FunctorsBranchingOutSpec extends AnyFunSpec {

  

    // Given tree
    sealed trait Tree[+A]
    final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
    final case class Leaf[A](value: A) extends Tree[A]
  
    // Functor impl
    implicit val treeFunctor = new Functor[Tree] {
      override def map[A, B](fa: Tree[A])(f: A => B): Tree[B] = {
        fa match {
          case leaf: Leaf[A] =>
            new Leaf[B](f(leaf.value))
          case branch: Branch[A] => {
            new Branch[B](map(branch.left)(f), map(branch.right)(f))
          }
        }
      }
    }

    it("maps over a Tree type Any -> String") {
      val sourceTree = Branch(
        Branch(
          Leaf(1),
          Leaf("2")
        ),
        Leaf(3.0)
      )

      val targetTree = Branch(
        Branch(
          Leaf("1"),
          Leaf("2")
        ),
        Leaf("3.0")
      )
    
      assert((sourceTree: Tree[Any]).map(
        _.toString()
      ) == targetTree)
    }



}
