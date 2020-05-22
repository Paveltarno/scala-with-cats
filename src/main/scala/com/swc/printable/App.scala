package com.swc.printable
import com.swc._

import PrintableInstances._
import PrintableSyntax._

import cats.instances.int._
import cats.instances.string._
import cats.instances.option._

import cats.Show
import cats.syntax.show._

object App {

  implicit val printableCat = new Printable[Cat] {

    override def format(input: Cat): String =
      s"${Printable.format(input.name)} is a ${Printable.format(input.age)} year old ${Printable
        .format(input.color)} cat"

  }

  implicit val showableCat: Show[Cat] = Show.show((input) =>
    s"${input.name.show} is a ${input.age.show} year old ${input.color.show} cat"
  )

  def run = {
    println("-- without cats --")
    val cat1 = new Cat("Yeti", 1, "Black")
    val cat2 = new Cat("Bubu", 3, "Yellow")
    println("----Using Instances----")
    Printable.print(cat1)
    Printable.print(cat2)
    println("----Using Syntax----")
    cat1.print
    cat2.print

    println("-- with cats --")
    println(cat1.show)
    println(cat2.show)
  }
}
