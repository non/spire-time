package spire.time.joda

import org.joda.time._
import spire.algebra._
import spire.math.Rational
import spire.std.any._

package object datetime
    extends DateTimeInstances

package object days
    extends DaysInstances

package object duration
    extends DurationInstances

package object hours
    extends HoursInstances

package object instant
    extends InstantInstances

package object localdate
    extends LocalDateInstances

package object localtime
    extends LocalTimeInstances

package object minutes
    extends MinutesInstances

package object months
    extends MonthsInstances

package object seconds
    extends SecondsInstances

package object weeks
    extends WeeksInstances

package object years
    extends YearsInstances

package object any
    extends DateTimeInstances
    with DaysInstances
    with DurationInstances
    with HoursInstances
    with LocalDateInstances
    with MinutesInstances
    with MonthsInstances
    with SecondsInstances
    with WeeksInstances
    with YearsInstances

trait DateTimeInstances {
  implicit val dateTimeOrder =
    Auto.order[DateTime]

  implicit val dateTimeMetricSpace =
    new MetricSpace[DateTime, Duration] {
      def distance(t1: DateTime, t2: DateTime): Duration =
        new Duration(t1, t2)
    }

  implicit val dateTimeTorsor =
    new Torsor[DateTime, Duration] {
      def actl(d: Duration, dt: DateTime): DateTime =
        dt.plus(d)
      def actr(dt: DateTime, d: Duration): DateTime =
        dt.plus(d)
      def diff(dt1: DateTime, dt2: DateTime): Duration =
        new Period(dt1, dt2).toStandardDuration
    }
}

trait DaysInstances {
  implicit val daysOrder = Auto.order[Days]
  implicit val daysAbGroup = Auto.abGroup[Days](Days.ZERO)
  implicit lazy val daysModuleInt = Auto.module[Days](Days.ZERO)
}

trait DurationInstances extends LowPriorityDurationInstances {
  implicit val durationOrder = Auto.order[Duration]
  implicit val durationAbGroup = Auto.abGroup[Duration](Duration.ZERO)

  implicit val durationModuleLong = new Module[Duration, Long] {
    implicit val scalar: Rng[Long] = Rng[Long]

    def zero: Duration = Duration.ZERO
    def negate(v: Duration): Duration = new Duration(-v.getMillis)
    def plus(v: Duration, w: Duration): Duration = v plus w
    override def minus(v: Duration, w: Duration): Duration = v minus w
    def timesl(r: Long, v: Duration): Duration = new Duration(v.getMillis * r)
  }
}

trait LowPriorityDurationInstances {
  val field = Field[Rational]
  implicit val durationInnerProductSpaceRational = new InnerProductSpace[Duration, Rational] {
    implicit val scalar: Field[Rational] = field

    def zero: Duration = Duration.ZERO
    def negate(v: Duration): Duration = new Duration(-v.getMillis)
    def plus(v: Duration, w: Duration): Duration = v plus w
    override def minus(v: Duration, w: Duration): Duration = v minus w
    def timesl(r: Rational, v: Duration): Duration = new Duration(v.getMillis * r.toLong)
    override def divr(v: Duration, r: Rational): Duration = new Duration((Rational(v.getMillis) / r).toLong)
    def dot(v: Duration, w: Duration): Rational = Rational(v.getMillis) * Rational(w.getMillis)
  }
}

trait HoursInstances {
  implicit val hoursOrder = Auto.order[Hours]
  implicit val hoursAbGroup = Auto.abGroup[Hours](Hours.ZERO)
  implicit val hoursModuleInt = Auto.module[Hours](Hours.ZERO)
}

trait InstantInstances {
  implicit val dateTimeOrder = Auto.order[Instant]
}

trait LocalDateInstances {
  implicit val localDateOrder = Auto.order[LocalDate]
}

trait LocalTimeInstances {
  implicit val localTimeOrder = Auto.order[LocalTime]
}

trait MinutesInstances {
  implicit val minutesOrder = Auto.order[Minutes]
  implicit val minutesAbGroup = Auto.abGroup[Minutes](Minutes.ZERO)
  implicit val minutesModuleInt = Auto.module[Minutes](Minutes.ZERO)
}

trait MonthsInstances {
  implicit val monthsOrder = Auto.order[Months]
  implicit val monthsAbGroup = Auto.abGroup[Months](Months.ZERO)
  implicit val monthsModuleInt = Auto.module[Months](Months.ZERO)
}

trait SecondsInstances {
  implicit val secondsOrder = Auto.order[Seconds]
  implicit val secondsAbGroup = Auto.abGroup[Seconds](Seconds.ZERO)
  implicit val secondsModuleInt = Auto.module[Seconds](Seconds.ZERO)
}

trait WeeksInstances {
  implicit val weeksOrder = Auto.order[Weeks]
  implicit val weeksAbGroup = Auto.abGroup[Weeks](Weeks.ZERO)
  implicit val weeksModuleInt = Auto.module[Weeks](Weeks.ZERO)
}

trait YearsInstances {
  implicit val yearsOrder = Auto.order[Years]
  implicit val yearsAbGroup = Auto.abGroup[Years](Years.ZERO)
  implicit val yearsModuleInt = Auto.module[Years](Years.ZERO)
}
