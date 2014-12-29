package spire.time.joda

import org.joda.time._

import spire.algebra.{Eq, AdditiveMonoid}
import spire.implicits._
import spire.laws._
//import spire.laws.SpireArbitrary._
import spire.math.Rational
import spire.time.joda.any._

import org.typelevel.discipline.Predicate
import org.typelevel.discipline.scalatest.Discipline

import org.scalatest.FunSuite
import org.scalacheck.{Arbitrary, Prop}
import org.scalacheck.Arbitrary._
import org.scalacheck.Prop._

object SmallInstances {
  def int8: Arbitrary[Int] = Arbitrary(arbitrary[Byte].map(_.toInt))
  def int16: Arbitrary[Int] = Arbitrary(arbitrary[Short].map(_.toInt))
  def long16: Arbitrary[Long] = Arbitrary(arbitrary[Short].map(_.toLong))
  def long32: Arbitrary[Long] = Arbitrary(arbitrary[Int].map(_.toLong))

  def rat16: Arbitrary[Rational] = Arbitrary(arbitrary[Short].map(n => Rational(n.toInt)))
}

class PackageTest extends FunSuite with Discipline {

  import SmallInstances._

  // joda time uses primtive types but is very sensitive about
  // overflow. this is obviously a bit fake but seems like the
  // only way to really do this stuff properly :/

  implicit val arbInt: Arbitrary[Int] = int8
  implicit val arbLong: Arbitrary[Long] = long32
  implicit val arbRational: Arbitrary[Rational] = rat16

  implicit val arbDateTime: Arbitrary[DateTime] =
    Arbitrary(arbitrary[Long].map(new DateTime(_)))

  implicit val arbDuration: Arbitrary[Duration] =
    Arbitrary(arbitrary[Int].map(n => new Duration(n.toLong)))

  implicit val arbDays: Arbitrary[Days] =
    Arbitrary(arbitrary[Int].map(Days.days(_)))

  // we can't run the metric space laws, since they erroneously
  // want an Rng[Duration] instead of just AdditiveMonoid[Duration].
  //
  // TODO: fix this upstream in spire.
  checkAll("DateTime", OrderLaws[DateTime].order)
  //checkAll("DateTime", VectorSpaceLaws[DateTime, Duration].metricSpace)

  checkAll("Duration", OrderLaws[Duration].order)
  checkAll("Duration", GroupLaws[Duration].abGroup)
  checkAll("Duration", VectorSpaceLaws[Duration, Long].module)
  checkAll("Duration", VectorSpaceLaws[Duration, Rational].innerProductSpace)

  checkAll("Days", OrderLaws[Days].order)
  checkAll("Days", GroupLaws[Days].abGroup)
  checkAll("Days", VectorSpaceLaws[Days, Int].module)
}
