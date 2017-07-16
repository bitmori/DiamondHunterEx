package me.kirkscope.game.manager

import me.kirkscope.game.state.*
import java.awt.Graphics2D

/**
 * Created by Kirk on 7/15/17.
 */
class StateManager {
    companion object {
        val INTRO = 0
        val MENU = 1
        val PLAY = 2
        val GAMEOVER = 3
        val NUM_STATES = 4
        var PLAY_TIME = 0L
    }
    var paused: Boolean = false
    private val pauseState: PauseState = PauseState(this)

    private val gameStates: Array<IState> = Array(NUM_STATES) { makeState(it) }

    var state = INTRO
        set (i) {
            field = i
            gameStates[i] = makeState(i)
        }

    fun makeState(i: Int): IState = when (i) {
        INTRO -> IntroState(this)
        MENU -> MenuState(this)
        PLAY -> PlayState(this)
        else -> GameOverState(this)
    }

    fun update() {
        if (paused) {
            pauseState.update()
        } else {
            gameStates[state].update()
        }
    }

    fun draw(gr: Graphics2D) {
        if (paused) {
            pauseState.draw(gr)
        } else {
            gameStates[state].draw(gr)
        }
    }
}