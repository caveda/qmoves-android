package com.quoders.apps.qmoves.data

import android.os.Parcelable
import com.quoders.apps.qmoves.tools.TimeUtils
import kotlinx.android.parcel.Parcelize
import java.time.DayOfWeek
import java.time.Instant

@Parcelize
data class Schedule (
    var workingDays: String?,
    var monday2Tuesday: String?,
    var friday: String?,
    var saturday: String?,
    var sunday: String?
): Parcelable {

    companion object {
        val SCHEDULE_SEPARATOR = ","
    }

    /**
     * Returns the next schedule arrival given the supplied day of the week.
     * Result is EPOCH seconds.
     */
    fun nextArrival(day: DayOfWeek) : Long  {
        return Instant.now().epochSecond + 120
    }

    private fun scheduleDayToEpochList (scheduleDay: String) : List<Long> {
        var result = mutableListOf<Long>()
        val stringTimes = scheduleDay.split(SCHEDULE_SEPARATOR)
        stringTimes.forEach { s -> result.add(TimeUtils.ToEpochSeconds(s)) }
        return result
    }
}
