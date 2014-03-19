package spire.time.joda

import spire.time.macros.JodaMacros

import language.experimental.macros

object Auto {
  def order[A] = macro JodaMacros.orderImpl[A]
  def abGroup[A](z: A) = macro JodaMacros.abGroupImpl[A]
  def module[A](z: A) = macro JodaMacros.moduleImpl[A]
}
