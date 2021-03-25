package com.quoders.apps.qmoves.realTime.model

/**
 * Entity containing the information of the
 * next transports for a stop.
 */
data class StopNextTransport(
    val lineId: String,
    val stopId: String,
    val arrivalTime: String)