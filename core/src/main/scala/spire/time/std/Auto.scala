package spire.time.macros

import language.experimental.macros

object Auto {
  def order[A] = macro JodaMacros.orderImpl[A]
  def abGroup[A](z: A) = macro JodaMacros.abGroupImpl[A]
  def module[A](z: A) = macro JodaMacros.moduleImpl[A]
}
