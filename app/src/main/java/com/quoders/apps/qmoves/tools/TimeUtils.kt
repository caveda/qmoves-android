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
         * Returns -1 if futureDate is older than now().
         */
        fun MinutesTo(futureDate: String):Long {
            try {
                val future = Instant.parse(futureDate)
                val now = Instant.now()
                val diff = Duration.between(now,future)
                return if (diff.isNegative) -1 else diff.toMinutes()
            }
            catch (ex: Exception) {
                Timber.e("Exception transforming to minutes: ${ex.message}")
                return -1
            }
        }
    }
}