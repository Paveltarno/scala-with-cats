package example

import org.scalatest.funspec.AnyFunSpec
import com.swc.printable.Printable
import com.swc.printable.PrintableInstances

final case class Box[A](value: A)

class ShowingOffContramapSpec extends AnyFunSpec {

  it("Uses contramap to convert from Printable[Int] to Printable[Double]") {
    val target =
      PrintableInstances.formatInt.contramap((in: String) => in.toInt)
    assert(target.format("2") == "2")
  }

  describe("With Box class") {

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

class ImapSpec extends AnyFunSpec {

  trait Codec[A] {
    self =>
    def encode(v: A): String
    def decode(v: String): A
    def imap[B](dec: A => B, enc: B => A): Codec[B] = {
      new Codec[B] {
        override def encode(v: B): String = self.encode(enc(v))
        override def decode(v: String): B = dec(self.decode(v))
      }
    }
  }

  def encode[A](value: A)(implicit c: Codec[A]): String = c.encode(value)
  def decode[A](value: String)(implicit c: Codec[A]): A = c.decode(value)

  implicit val codecString = new Codec[String] {
    override def encode(v: String): String = v
    override def decode(v: String): String = v
  }

  implicit val codecDouble =
    codecString.imap[Double]((x) => x.toDouble, (x) => x.toString())
  it("Transforms Codec[String] -> Codec[Double]") {

    assert(encode(2.0) == "2.0")
    assert(decode[Double]("2.0") == 2.0)

  }

  it("Transforms Code[String] to Code[Box[Double]]") {

    implicit def codecBox[A](implicit codec: Codec[A]): Codec[Box[A]] = {
      codec.imap[Box[A]](
        Box(_),
        _.value
      )
    }

    assert(encode(Box(123.4)) == "123.4")
    assert(decode[Box[Double]]("123.4") == Box(123.4))
  }

}
