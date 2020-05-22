package com.swc.printable
import scala.specialized
// P18 1.3 Exercise: Printable Library

// type class
trait Printable[A] {
  def format(input: A): String
}

// instances
object PrintableInstances {

  implicit val printableString = new Printable[String] {
    override def format(input: String): String = input
  }

  implicit val formatInt = new Printable[Int] {
    override def format(input: Int): String = input.toString()
  }

}

// interfaces
object Printable {
  def format[A](input: A)(implicit printable: Printable[A]) = {
    printable.format(input)
  }

  def print[A](input: A)(implicit printable: Printable[A]) = {
    println(printable.format(input))
  }
}

object PrintableSyntax {
  implicit class PrintableOps[A](input: A) {
    def format(implicit printable: Printable[A]) = printable.format(input)
    def print(implicit printable: Printable[A]): Unit =
      println(printable.format(input))
  }
}
