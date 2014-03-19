## Spire-Time

This library provides type class instances allowing Spire users to
work with date and time objects.

Currently the library focuses on compatibility with
[Joda Time](http://www.joda.org/joda-time/).

This README will eventually have more information.

Spire-time is provided to you as free software under the MIT license.

### Example

Here are some basic examples of using this library with Spire:

```scala
import spire.algebra._
import spire.implicits._
import spire.time.joda.any._

import org.joda.time._

// uses spire.algebra.Order[DateTime]
def sortDateTimes(ds: Seq[DateTime]): Seq[DateTime] =
  ds.qsorted

// uses spire.algebra.Module[Duration] to sum a list
def sumDurations(ds: List[Duration]): Duration =
  ds.qsum

// determines how long a task will take
def total(timePerDog: Duration, numDogs: Int, timePerCat: Duration, numCats: Int): Duration =
  (timePerDog :* numDogs) + (timePerCat :* numCats)
```

### Credits

Copyright 2014 Erik Osheim

The MIT software license is attached in the COPYING file.
