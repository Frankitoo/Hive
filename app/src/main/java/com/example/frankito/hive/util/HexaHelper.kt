package com.example.frankito.hive.util

import com.example.frankito.hive.ui.view.HexaElement

object HexaHelper {
    fun checkIfTwoElementsAreNeighbour(element1: HexaElement, element2: HexaElement): Boolean {

        val row1 = element1.currentRow!!
        val col1 = element1.currentCol!!
        val row2 = element2.currentRow!!
        val col2 = element2.currentCol!!

        if ((row1 == row2 - 1) && (col1 == col2 + 1)) {
            return true
        }
        if ((row1 == row2) && (col1 == col2 + 1)) {
            return true
        }
        if ((row1 == row2 + 1) && (col1 == col2)) {
            return true
        }
        if ((row1 == row2 + 1) && (col1 == col2 - 1)) {
            return true
        }
        if ((row1 == row2) && (col1 == col2 - 1)) {
            return true
        }
        if ((row1 == row2 - 1) && (col1 == col2)) {
            return true
        }
        return false
    }
}
