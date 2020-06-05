package example

import org.scalatest.funspec.AnyFunSpec
import com.swc.printable.Printable
import com.swc.printable.PrintableInstances

class ShowingOffContramapSpec extends AnyFunSpec {

  it("Uses contramap to convert from Printable[Int] to Printable[Double]") {
    val target =
      PrintableInstances.formatInt.contramap((in: String) => in.toInt)
    assert(target.format("2") == "2")
  }

  describe("With Box class") {

    final case class Box[A](value: A)

    implicit val stringPrintable: Printable[String] =
      new Printable[String] {
        def format(value: String): String =
          "\"" + value + "\""
      }

    implicit val booleanPrintable: Printable[Boolean] = new Printable[Boolean] {
      def format(value: Boolean): String =
        if (value) "yes" else "no"
    }

    implicit def boxPrintable[A](implicit printable: Printable[A]) = {
      printable.contramap[Box[A]](_.value)
    }

    it("Should format Box[String]") {
      println("HELLO " + Printable.format(Box("hello world")))
      assert(Printable.format(Box("hello world")) == "\"hello world\"")
    }

    it("Should format Box[Boolean]") {
      assert(Printable.format(Box(true)) == "yes")
    }
  }

}
