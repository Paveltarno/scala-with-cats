package com.swc.usingCats

import com.swc.Cat
import java.util.Date
import cats.Eq
import cats.syntax.eq._
import cats.syntax.option._
import cats.instances.int._
import cats.instances.long._
import cats.instances.option._

object eqExamples {

  implicit val dateEq: Eq[Date] =
    Eq.instance((a, b) => a.getTime() === b.getTime())

  implicit val catEq: Eq[Cat] =
    Eq.instance((a, b) => a == b)

  def run() = {
    println(s"Dates not eq: ${new Date() === new Date()}")

    val cat1 = Cat("Garfield", 38, "orange and black")
    val cat11 = Cat("Garfield", 38, "orange and black")
    val cat2 = Cat("Heathcliff", 33, "orange and black")
    println(s"Cats eq ${cat1 === cat11}")
    println(s"Cats ineq ${cat1 =!= cat2}")

    val optionCat1 = Option(cat1)
    val optionCat2 = Option.empty[Cat]
    println(s"Cat optional ineq ${optionCat1 =!= optionCat2}")
  }
}
