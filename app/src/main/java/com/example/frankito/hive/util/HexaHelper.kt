package com.example.frankito.hive.util

import com.example.frankito.hive.model.Direction
import com.example.frankito.hive.model.HexaCell
import com.example.frankito.hive.ui.view.HexaElement

object HexaHelper {

    fun checkIfTwoElementsAreNeighbour(element1: HexaElement, element2: HexaElement): Boolean {

        val row1 = element1.currentRow!!
        val col1 = element1.currentCol!!
        val row2 = element2.currentRow!!
        val col2 = element2.currentCol!!

        return checkIfCoordinatesAreNeightbour(row1, col1, row2, col2)
    }

    fun checkIfTwoCellsAreNeighbour(cell1: HexaCell, cell2: HexaCell): Boolean {

        val row1 = cell1.row
        val col1 = cell1.col
        val row2 = cell2.row
        val col2 = cell2.col

        return checkIfCoordinatesAreNeightbour(row1, col1, row2, col2)
    }

    fun checkIfCoordinatesAreNeightbour(row1: Int, col1: Int, row2: Int, col2: Int): Boolean {

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

    fun checkCellContainsElement(hexaCell: HexaCell): Boolean {
        return hexaCell.layout.childCount > 0
    }

    fun checkCellContainsMulitpleElements(hexaCell: HexaCell): Boolean {
        return hexaCell.layout.childCount > 1
    }

    fun getDirectionOfNeighbourElement(row1: Int, col1: Int, row2: Int, col2: Int): com.example.frankito.hive.model.Direction? {
        var direction: Direction? = null
        if ((row1 - 1 == row2) && (col1 + 1 == col2)) {
            direction = Direction(-1, +1)
        }
        if ((row1 == row2) && (col1 + 1 == col2)) {
            direction = Direction(0, +1)
        }
        if ((row1 + 1 == row2) && (col1 == col2)) {
            direction = Direction(+1, 0)
        }
        if ((row1 + 1 == row2) && (col1 - 1 == col2)) {
            direction = Direction(+1, -1)
        }
        if ((row1 == row2) && (col1 - 1 == col2)) {
            direction = Direction(0, -1)
        }
        if ((row1 - 1 == row2) && (col1 == col2)) {
            direction = Direction(-1, 0)
        }
        return direction
    }

}
