package com.quoders.apps.qmoves.data.source.local

/***
 * DB object representing a line with all its associated information
 */
data class DBFullLine (
    val line: DBLine,
    val stops: List<DBStop>,
    val route: List<DBRouteLocation>)