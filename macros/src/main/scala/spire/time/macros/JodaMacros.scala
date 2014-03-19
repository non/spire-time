package spire.time.macros

import scala.reflect.macros.Context

import spire.algebra.{AbGroup, Eq, Module, Order, Rng}

object JodaMacros {
  def orderImpl[A: c.WeakTypeTag](c: Context): c.Expr[Order[A]] =
    JodaAlgebra[c.type](c).Order[A]()

  def abGroupImpl[A: c.WeakTypeTag](c: Context)(z: c.Expr[A]): c.Expr[AbGroup[A]] =
    JodaAlgebra[c.type](c).AbGroup[A](z)

  def moduleImpl[A: c.WeakTypeTag](c: Context)(z: c.Expr[A]): c.Expr[Module[A, Int]] =
    JodaAlgebra[c.type](c).Module[A](z)
}
