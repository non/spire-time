package spire.time.joda

import spire.algebra._
import spire.time.macros.JodaMacros

import language.experimental.macros

object Auto {
  def order[A]: Order[A] =
    macro JodaMacros.orderImpl[A]

  //def abGroup[A](z: A) =
  //  macro JodaMacros.abGroupImpl[A]

  def abGroup[A](z: A): AdditiveAbGroup[A] =
    macro JodaMacros.additiveAbGroupImpl[A]

  def module[A](z: A): Module[A, Int] =
    macro JodaMacros.moduleImpl[A]
}
