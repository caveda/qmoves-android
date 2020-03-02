package com.quoders.apps.qmoves.data

/**
  Entity representing a stop of a Line
 */
data class Stop (
    val id: String,
    val name: String,
    val schedule: Schedule,
    val location: Location
)

data class Schedule (
    val workingDays: String,
    val monday2Tuesday: String,
    val friday: String,
    val saturday: String,
    val sunday: String
)
