package com.quoders.apps.qmoves.tools

import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.Temporal
import java.util.*
import java.util.logging.SimpleFormatter

class TimeUtils {
    companion object {
        /**
         * Receives a date in EPOCH seconds and returns how many
         * minutes are between the input and now.
         * Returns -1 if futureDate is older than now.
         */
        fun MinutesTo(epochSeconds: Long):Long {
            try {
                val future = Instant.ofEpochSecond(epochSeconds)
                val now = Instant.now()
                val diff = Duration.between(now,future)
                return if (diff.isNegative) -1 else diff.toMinutes()
            }
            catch (ex: Exception) {
                Timber.e("Exception transforming to minutes: ${ex.message}")
                return -1
            }
        }

        /**
         * Receives a string date in standard ISO 8601 and converts it into
         * epoch seconds.
         * Returns -1 if the format of the input string is invalid.
         */
        fun  ToEpochSeconds(dateTimeStr: String):Long {
            try {
                val dateFormatted = DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(dateTimeStr)
                val dateTime = OffsetDateTime.from(dateFormatted)
                return dateTime.toEpochSecond()
            }
            catch (ex: Exception) {
                Timber.e("Exception normalizing $dateTimeStr: ${ex.message}")
                return -1
            }
        }
    }
}