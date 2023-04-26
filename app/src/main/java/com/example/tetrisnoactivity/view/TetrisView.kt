package com.example.tetrisnoactivity.view

import android.content.Context
import android.graphics.Paint
import android.os.Message
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
//import androidx.annotation.Dimension
import com.example.tetrisnoactivity.GameActivity
import com.example.tetrisnoactivity.models.AppModel


class TetrisView : View {
    private val paint = Paint()
    private val lastMove: Long = 0
    private val model: AppModel? = null
    private val activity: GameActivity? = null
    private val viewHandler = ViewHandler(this)
    private val cellSize: Dimension = Dimension(0, 0)
    private val frameOffset: Dimension = Dimension(0, 0)

    constructor(context: Context, attrs: AttributeSet) :
            super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) :
            super(context, attrs, defStyle)

    companion object {
        private val DELAY = 500
        private val BLOCK_OFFSET = 2
        private val FRAME_OFFSET_BASE = 10
    }

    private class ViewHandler(private val owner: TetrisView) : Handler() {
        override fun handleMessage(message: Message) {
            if (message.what == 0) {
                if (owner.model!!.isGameOver()) {
                    owner.model?.endGame()
                    Toast.makeText(owner.activity, "Game Ower", Toast.LENGTH_LONG).show();
                }
                if (owner.model!!.isGameActive()) {
                    owner.setGameCommandWithDelay(AppModel.Motions.DOWN)
                }
            }

        }
    }

    fun sleep(delay: Long) {
        this.removeMessage(0)
        sendMessageDelayed(obtianMessage(0), delay)
    }

    private data class Dimension(val width: Int, val height: Int)

    fun setModel(model: AppModel) {
        this.model = model
    }

    fun setActivity(gameActivity: GameActivity) {
        this.activity = gameActivity
    }

    fun setGameCommand(move: AppModel.Motions) {
        if (null != model && (model?.currentState == AppModel.Statusus.ACTIVE.name)) {
            if (AppModel.Motions.DOWN == move) {
                model?.generateField(move.name)
                invalidate()
                return
            }
            setGameCommandWithDelay(move)
        }
    }
    fun setGameCommandWithDelay(move: AppModel.Motions) {
        val now = System.currentTimeMillis()
        if (now - lastMove > DELAY) {
            model?.generateField(move.name)
            invalidate()
            lastMove = now
        }
        updateScores()
        viewHandler.sleep(DELAY.toLong())
    }
    private fun updateScores() {
    activity?.tvCurrentScore?.text = "${model.score}"
        activity?.tvHighScore?.text = "${activity?.appPreferences?.getHighScore()}"
    }
}
