package com.quoders.apps.qmoves.tools

import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.Temporal
import java.util.*
import java.util.logging.SimpleFormatter

class TimeUtils {
    companion object {
        /**
         * Receives a date in ISO 8601 and returns how many minutes
         * from now to the passed date.
         */
        fun MinutesTo(futureDate: String):Long {

            try {
                val future = Instant.parse(futureDate)
                val now = Instant.now()
                val diff = Duration.between(future,now)
                return diff.toMinutes()
                /*
                Duration.
                Date.from(Instant.now())
                var futureDateTime = LocalDate.parse(futureDate)
                futureDateTime.to
                futureDateTime.rangeTo()
                var nowDateTime = LocalDate.now()
                var diff = futureDateTime.minus(nowDateTime.toEpochDay())
*/

            }
            catch (ex: Exception) {
                Timber.e("Exception transforming to minutes: ${ex.message}")
                return 0
            }
        }
    }
}