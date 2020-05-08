package com.quoders.apps.qmoves.data.mapper

/**
 * Generic common interface for mapper classes
 */
interface Mapper<I, O> {
    fun map(t: I): O
}

