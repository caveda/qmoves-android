package com.quoders.apps.qmoves.data

import org.junit.Test
import com.google.common.truth.Truth.assertThat
import com.quoders.apps.qmoves.data.Line.*
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.runner.RunWith

@RunWith(JUnitParamsRunner::class)
class LineTest {

    @Test
    @Parameters(value = [ "FORWARD", "BACKWARD"])
    fun getUniqueId_directionSupplied_uniqueIdEndWithDirectionCode(direction: Direction) {
        // Given
        val line = Line("10","any",direction, LineType.REGULAR)

        // Then
        assertThat(line.uniqueId).isEqualTo("10${direction.code}")
    }
}