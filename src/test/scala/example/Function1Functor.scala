import org.scalatest.funspec.AnyFunSpec

class Function1Functor extends AnyFunSpec {

  trait MappableFn[F[_, _]] {
    def map[A, B, C](fa: F[A, B])(fn: B => C): F[A, C]
  }

  implicit val Mappable = new MappableFn[Function1] {
    override def map[A, B, C](fa: A => B)(fn: B => C): A => C =
      (in: A) => fn(fa(in))
  }

  implicit class Function1Ops[F[_, _], A, B](in: F[A, B]) {
    def map[C](fn: B => C)(implicit mapper: MappableFn[F]): F[A, C] = {
      mapper.map(in)(fn)
    }
  }

  describe("Function1Functor (no cats)") {

    it("'nothing'") {
      trait Helloable[T[_]]{
        def hello[A,B](in: T[A]): T[B]
      }

      val q = new Helloable[Option] {
        override def hello[A, B](in: Option[A]): Option[B] = ???
      }
    }

    it("Maps Int => Double => String") {
      val a: Int => Double = (in) => in.toDouble
      val b: Double => String = (in) => in.toString()
      val c = a.map(b)
      assert(c(234) == "234.0")
    }

    it("Maps A => B => C") {
      val a: Int => String = (in) => in.toString()
      val b = (in: String) => in.toCharArray()
      val c = a.map(b)
      assert(c(234) === Array('2', '3', '4'))
    }

  }
}
