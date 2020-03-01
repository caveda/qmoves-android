package com.quoders.apps.qmoves.data

/*
  Entity representing a stop of a Line
 */
data class Stop (
    var id: String,
    var name: String,
    var schedule: Schedule,
    var location: Location
)

data class Schedule (
    var workingDays: String,
    var monday2Tuesday: String,
    var friday: String,
    var saturday: String,
    var sunday: String
)
