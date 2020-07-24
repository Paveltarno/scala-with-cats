package example

import org.scalatest.funspec.AnyFunSpec
import cats.data.Reader
import cats.syntax.applicative._

class HackingOnReaders extends AnyFunSpec {

  final case class Db(
      usernames: Map[Int, String],
      passwords: Map[String, String]
  )

  type DbReader[A] = Reader[Db, A]

  def findUsername(userId: Int): DbReader[Option[String]] =
    Reader(_.usernames.get(userId))

  def checkPassword(username: String, password: String): DbReader[Boolean] =
    Reader(_.passwords.get(username).contains(password))

  def checkLogin(userId: Int, password: String): DbReader[Boolean] = {
    for {
      maybeUsername <- findUsername(userId)
      isPassword <- maybeUsername
        .map((username) => {
          checkPassword(username, password)
        })
        .getOrElse(false.pure[DbReader])
    } yield isPassword
  }

  val instance = Db(
    Map(
      1 -> "Pavel",
      2 -> "Yeti"
    ),
    Map(
      "Pavel" -> "admin",
      "Yeti" -> "1234"
    )
  )

  describe("#findUsername") {

    it("finds an existing user") {
      assert(findUsername(1).run(instance) == Some("Pavel"))
    }

    it("doesnt find a non existing user") {
      assert(findUsername(5).run(instance) == None)
    }
  }

  describe("#checkPassword") {

    it("finds an existing user's pass") {
      assert(checkPassword("Pavel", "admin").run(instance) == true)
    }

    it("doesnt find a existing user's wrong pass") {
      assert(checkPassword("Blabla", "qwer").run(instance) == false)
    }

    it("doesnt find a non existing user pass") {
      assert(checkPassword("Blabla", "admin").run(instance) == false)
    }
  }

  describe("#checkLogin") {

    assert(checkLogin(1, "admin").run(instance) == true)
    assert(checkLogin(2, "admin").run(instance) == false)
    assert(checkLogin(1, "wrong").run(instance) == false)

  }
}
