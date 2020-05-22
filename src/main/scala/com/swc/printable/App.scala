package com.swc.printable
import PrintableInstances._
import PrintableSyntax._

case class Cat(name: String, age: Int, color: String)

object Cat {

  implicit val printableCat = new Printable[Cat] {

    override def format(input: Cat): String =
      s"${Printable.format(input.name)} is a ${Printable.format(input.age)} year old ${Printable
        .format(input.color)} cat"

  }

}

class App {
  def run = {
    val cat1 = new Cat("Yeti", 1, "Black")
    val cat2 = new Cat("Bubu", 3, "Yellow")
    println("----Using Instances----")
    Printable.print(cat1)
    Printable.print(cat2)
    println("----Using Syntax----")
    cat1.print
    cat2.print
  }
}
