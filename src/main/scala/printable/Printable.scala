// P18 1.3 Exercise: Printable Library

// type class
trait Printable[A]{
  def format(input: A): String
}