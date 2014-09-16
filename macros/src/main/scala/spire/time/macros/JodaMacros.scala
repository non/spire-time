package spire.time.macros

import scala.reflect.macros.Context

import spire.algebra.{AdditiveAbGroup, AbGroup, Eq, Module, Order, Rng}

object JodaMacros {
  def orderImpl[A: c.WeakTypeTag](c: Context): c.Expr[Order[A]] =
    JodaAlgebra[c.type](c).Order[A]()

  // def abGroupImpl[A: c.WeakTypeTag](c: Context)(z: c.Expr[A]): c.Expr[AbGroup[A]] =
  //   JodaAlgebra[c.type](c).AbGroup[A](z)

  def additiveAbGroupImpl[A: c.WeakTypeTag](c: Context)(z: c.Expr[A]): c.Expr[AdditiveAbGroup[A]] =
    JodaAlgebra[c.type](c).AdditiveAbGroup[A](z)

  def moduleImpl[A: c.WeakTypeTag](c: Context)(z: c.Expr[A]): c.Expr[Module[A, Int]] =
    JodaAlgebra[c.type](c).Module[A](z)

  // def torsorImpl[A: c.WeakTypeTag, B: c.WeakTypeTag](c: Context): c.Expr[Torsor[A, B]] =
  //   JodaAlgebra[c.type](c).Torsor[A, B]()
}
