package com.example.tetrisnoactivity.models

import android.graphics.Point
import com.example.tetrisnoactivity.constants.CellConstants
import com.example.tetrisnoactivity.constants.FieldConstants
import com.example.tetrisnoactivity.helper.array2dOfByte
import com.example.tetrisnoactivity.storage.AppPreferences


class AppModel {
    var score: Int = 0
    private var preferences: AppPreferences? = null

    var currentBlock: Block? = null
    var currentState: String = Statusus.AWAITING_START.name
    private var field: Array<ByteArray> = array2dOfByte(
        FieldConstants.ROW_COUNT.value,
        FieldConstants.COLUMN_COUNT.value
    )

    fun setPreferences(preferences: AppPreferences?) {
        this.preferences = preferences
    }

    fun getCellStatus(row: Int, column: Int): Byte? {
        return field[row][column]
    }

    private fun setCellStatus(row: Int, column: Int, status: Byte?) {
        if (status != null) {
            field[row][column] = status
        }
    }

    fun isGameOver(): Boolean {
        return currentState == Statusus.OVER.name
    }

    fun isGameActive(): Boolean {
        return currentState == Statusus.ACTIVE.name
    }

    fun isGameAwaitingStart(): Boolean {
        return currentState == Statusus.ACTIVE.name
    }

    private fun boostScore() {
        score += 10
        if (score > preferences?.getHighScore() as Int) preferences?.saveHighScore(score)
    }

    private fun generateNextBlock() {
        currentBlock = Block.createBlock()
    }

    private fun validTranslation(position: Point, shape: Array<ByteArray>): Boolean {
        return if (position.y < 0 || position.x < 0) {
            false
        } else if (position.y + shape.size > FieldConstants.ROW_COUNT.value) {
            false
        } else if (position.x + shape[0].size > FieldConstants.COLUMN_COUNT.value) {
            false
        } else {
            for (i in 0 until shape.size) {
                for (j in 0 until shape[i].size) {
                    val y = position.y + i
                    val x = position.x + j
                    if (CellConstants.EMPTY.value != shape[i][j] && CellConstants.EMPTY.value != field[y][x]) return false
                }
            }


        }


    }

    enum class Statusus {
        AWAITING_START, ACTIVE, INACTIVE, OVER
    }

    enum class Motions {
        LEFT, RIGHT, DOWN, ROTATE
    }
}