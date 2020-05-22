package com.swc.usingCats

import cats.Show

// For method interface
import cats.instances.string._
import cats.instances.int._

// For syntax interface
import cats.syntax.show._

object showExamples {

  def methodInterface() = {
    println(Show[Int].show(123))
    println(Show[String].show("Stringo"))
  }

  def syntaxInterface() = {
    println(456.show)
    println("string syntax".show)
  }

}
