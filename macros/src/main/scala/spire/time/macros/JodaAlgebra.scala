package spire.time.macros

import language.experimental.macros
import scala.reflect.macros.Context

import spire.algebra.{AbGroup, Eq, Module, Order, Rng}

case class JodaAlgebra[C <: Context](c: C) extends AutoOps { ops =>

  import c.universe._

  def plus[A: c.WeakTypeTag] = 
    binopSearch[A]("plus" :: Nil) getOrElse failedSearch("plus", "+")
  def minus[A: c.WeakTypeTag] = 
    binopSearch[A]("minus" :: Nil) getOrElse failedSearch("minus", "-")

  def negate[A: c.WeakTypeTag] = 
    unopSearch[A]("negated" :: Nil) getOrElse {
      c.Expr[A](Apply(
        Select(Ident(newTermName("id")), newTermName("minus")),
        List(Ident(newTermName("x")))))
    }

  def timesr[A: c.WeakTypeTag] = 
    binopSearch[A]("multipliedBy" :: Nil) getOrElse failedSearch("multipliedBy", ":*")

  def equals = binop[Boolean]("equals")

  def compare = binop[Int]("compareTo")

  def Eq[A: c.WeakTypeTag](): c.Expr[Eq[A]] = reify {
    new Eq[A] {
      def eqv(x: A, y: A): Boolean = ops.equals.splice
    }
  }
  
  def Order[A: c.WeakTypeTag](): c.Expr[Order[A]] = reify {
    new Order[A] {
      override def eqv(x: A, y: A): Boolean = ops.equals.splice
      def compare(x: A, y: A): Int = ops.compare.splice
    }
  }

  def AbGroup[A: c.WeakTypeTag](z: c.Expr[A]): c.Expr[AbGroup[A]] = reify {
    new AbGroup[A] {
      def id: A = z.splice
      def op(x: A, y: A): A = ops.plus.splice
      def inverse(x: A): A = ops.negate.splice
      override def opInverse(x: A, y: A): A = ops.minus.splice
    }
  }

  def Module[A: c.WeakTypeTag](z: c.Expr[A]): c.Expr[Module[A, Int]] = reify {
    new Module[A, Int] {
      implicit val scalar: Rng[Int] = Rng[Int]

      def zero: A = z.splice
      def negate(x: A): A = ops.negate.splice
      def plus(x: A, y: A): A = ops.plus.splice
      override def minus(x: A, y: A): A = ops.minus.splice
      override def timesr(x: A, y: Int): A = ops.timesr.splice
      def timesl(x: Int, y: A): A = timesr(y, x)
    }
  }
}

abstract class AutoOps {
  val c: Context
  import c.universe._

  def unop[A](name: String, x: String = "x"): c.Expr[A] =
    c.Expr[A](Select(Ident(newTermName(x)), newTermName(name)))

  def binop[A](name: String, x: String = "x", y: String = "y"): c.Expr[A] =
    c.Expr[A](Apply(
      Select(Ident(newTermName(x)), newTermName(name)),
      List(Ident(newTermName(y)))))

  def binopSearch[A: c.WeakTypeTag](names: List[String], x: String = "x", y: String = "y"): Option[c.Expr[A]] =
    names find { name => hasMethod1[A, A, A](name) } map (binop[A](_, x, y))

  def unopSearch[A: c.WeakTypeTag](names: List[String], x: String = "x"): Option[c.Expr[A]] =
    names find { name => hasMethod0[A, A](name) } map (unop[A](_, x))

  def hasMethod0[A: c.WeakTypeTag, B: c.WeakTypeTag](name: String): Boolean = {
    val tpeA = c.weakTypeTag[A].tpe
    val tpeB = c.weakTypeTag[B].tpe
    tpeA.members exists { m =>
      m.isMethod && m.isPublic && m.name.encoded == name && (m.typeSignature match {
        case MethodType(Nil, ret) => ret <:< tpeB
        case _ => false
      })
    }
  }

  // HACK: type-checking removed for now because Joda's types are somewhat weird.
  def hasMethod1[A: c.WeakTypeTag, B: c.WeakTypeTag, C: c.WeakTypeTag](name: String): Boolean = {
    val tpeA = c.weakTypeTag[A].tpe
    val tpeB = c.weakTypeTag[B].tpe
    val tpeC = c.weakTypeTag[C].tpe
    tpeA.members exists { m =>
      m.isMethod && m.isPublic && m.name.encoded == name && (m.typeSignature match {
        case MethodType(List(param), ret) => true // HACK
        case _ => false
      })
    }
  }

  def failedSearch(name: String, op: String): c.Expr[Nothing] =
    c.abort(c.enclosingPosition,
      "Couldn't find matching method for op %s (%s)." format (name, op))
}
