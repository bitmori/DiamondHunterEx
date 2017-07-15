package me.kirkscope.game.state

import me.kirkscope.game.GamePanel
import me.kirkscope.game.manager.Content
import me.kirkscope.game.manager.JukeBox
import me.kirkscope.game.manager.KeyManager
import me.kirkscope.game.manager.StateManager
import java.awt.Color
import java.awt.Graphics2D

/**
 * Created by Kirk on 7/15/17.
 */
class GameOverState(val stateManager: StateManager): IState {
    companion object {
        val COLOR = Color(164, 198, 222)
    }
    val ticks = StateManager.PLAY_TIME
    val rank = when (ticks) {
        in 0 .. 3600 -> 1
        in 3600..5400 -> 2
        in 5400..7200 -> 3
        else -> 4
    }

    override fun draw(gr: Graphics2D) {
        gr.color = COLOR
        gr.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT2)

        Content.drawString(gr, "finish time", 20, 36)

        val minutes = (ticks / 1800).toInt()
        val seconds = (ticks / 30 % 60).toInt()
        val timeString = "%02d:%02d".format(minutes, seconds)
        Content.drawString(gr, timeString, 44, 48)
        Content.drawString(gr, "rank", 48, 66)
        val (title, x) = when (rank) {
            1 -> Pair("speed demon", 20)
            2 -> Pair("adventurer", 24)
            3 -> Pair("beginner", 32)
            else -> Pair("bumbling idiot", 8)
        }
        Content.drawString(gr, title, x, 78)
        Content.drawString(gr, "press any key", 12, 110)
    }

    override fun handleInput() {
        if (KeyManager.isPressed(KeyManager.ENTER)) {
            stateManager.state = StateManager.MENU
            JukeBox.play("collect")
        }
    }

    override fun update() {}
}