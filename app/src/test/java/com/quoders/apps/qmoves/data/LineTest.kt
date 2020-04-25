package com.quoders.apps.qmoves.data

import com.google.common.truth.Truth.assertThat
import com.quoders.apps.qmoves.data.Line.Direction
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(JUnitParamsRunner::class)
class LineTest {

    @Test
    @Parameters(value = [ "FORWARD", "BACKWARD"])
    fun getUniqueId_directionSupplied_uniqueIdEndWithDirectionCode(direction: Direction) {
        // Given
        val line = Line(0, 0, "I10", "10", "any-other", direction, false)

        // Then
        assertThat(line.uniqueId).isEqualTo("10${direction.code}")
    }
}