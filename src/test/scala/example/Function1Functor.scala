import org.scalatest.funspec.AnyFunSpec

class Function1Functor extends AnyFunSpec {

  trait MappableFn[A, B] {
    def map[C](fn: B => C): A => C
  }

  implicit val Mappable = new MappableFn[Int, Double] {
    override def map[C](fn: Double => C): Int => C = ???
  }

  implicit class Function1Ops[A, B](fn: Function1[A, B]) {
    def map[C](fna: B => C)(
        implicit mapper: MappableFn[A, B]
    ): Function1[A, C] = { (in: A) => fna(fn(in)) }
  }

  val a: Int => Double = (in) => in.toDouble
  val b: Double => String = (in) => in.toString()

  val z = a.map(b)

  describe("Function1Functor (no cats)") {

    it("Maps Int => Double => String") {
      val a: Int => Double = (in) => in.toDouble
      val b: Double => String = (in) => in.toString()
      val c = a.map(b)
      assert(c(234) == "234.0")
    }

    // it("Maps A => B => C") {
    //   val a: Int => String = (in) => in.toString()
    //   val b = (in: String) => in.toCharArray()
    //   val c = a.map(b)
    //   assert(c(234) == Array('2', '3', '4'))
    // }

  }
}
