package com.swc.usingCats

import cats.Show

// For method interface
import cats.instances.string._
import cats.instances.int._

// For syntax interface
import cats.syntax.show._

import java.util.Date

object showExamples {

  // Implement a new instance

  // implicit val dateShow: Show[Date] =
  //   new Show[Date] {

  //     override def show(date: Date): String =
  //       s"${date.getTime}ms since the epoch."

  //   }

  // OR use a Cats helper constructor

  implicit val dateShow: Show[Date] =
    Show.show(date => s"${date.getTime}ms since the epoch.")

  def run = {
    methodInterface()
    syntaxInterface()
    dateExt()
  }

  def methodInterface() = {
    println(Show[Int].show(123))
    println(Show[String].show("Stringo"))
  }

  def syntaxInterface() = {
    println(456.show)
    println("string syntax".show)
  }

  def dateExt() = {
    val curr = new Date()
    println(curr.show)
  }

}
