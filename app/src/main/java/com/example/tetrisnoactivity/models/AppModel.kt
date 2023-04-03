package com.example.tetrisnoactivity.models
import android.graphics.Point
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

    fun setPreferences(preferences: AppPreferences?){
        this.preferences = preferences
    }

    fun getCellStatus(row: Int, column: Int) : Byte? {
        return field[row][column]
    }
    private fun setCellStatus(row: Int, column: Int, status: Byte?) {
        if(status != null){
            field[row][column] = status
        }
    }

    fun isGameOver(): Boolean {
        return currentState == Statusus.OVER.name
    }
    fun isGameActive(): Boolean {
        return currentState == Statusus.ACTIVE.name
    }



    enum class Statusus {
        AWAITING_START, ACTIVE, INACTIVE, OVER
    }

    enum class Motions {
        LEFT, RIGHT, DOWN, ROTATE
    }
}