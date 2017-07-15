package me.kirkscope.game.manager

import java.awt.event.KeyEvent

/**
 * Created by Kirk on 7/15/17.
 */
object KeyManager {
    val UP = 0
    val LEFT = 1
    val DOWN = 2
    val RIGHT = 3
    val SPACE = 4
    val ENTER = 5
    val ESCAPE = 6
    val F1 = 7

    val NUM_KEYS = 8
    var keyState = BooleanArray(NUM_KEYS)
    var prevKeyState = BooleanArray(NUM_KEYS)

    fun keySet(k: Int, state: Boolean) {
        when (k) {
            KeyEvent.VK_UP -> keyState[UP] = state
            KeyEvent.VK_LEFT -> keyState[LEFT] = state
            KeyEvent.VK_DOWN -> keyState[DOWN] = state
            KeyEvent.VK_RIGHT -> keyState[RIGHT] = state
            KeyEvent.VK_SPACE -> keyState[SPACE] = state
            KeyEvent.VK_ENTER -> keyState[ENTER] = state
            KeyEvent.VK_ESCAPE -> keyState[ESCAPE] = state
            KeyEvent.VK_F1 -> keyState[F1] = state
            else -> {}
        }
    }

    fun update() = (0..NUM_KEYS - 1).forEach { i -> prevKeyState[i] = keyState[i] }

    fun isPressed(i: Int) = keyState[i] && !prevKeyState[i]

    fun isDown(i: Int) = keyState[i]

    fun anyKeyDown() = (0..NUM_KEYS - 1).any { keyState[it] }

    fun anyKeyPress() = (0..NUM_KEYS - 1).any { keyState[it] && !prevKeyState[it] }
}