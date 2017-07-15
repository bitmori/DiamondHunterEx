package me.kirkscope.game.state

import me.kirkscope.game.manager.StateManager
import java.awt.Graphics2D

/**
 * Created by Kirk on 7/15/17.
 */
interface IState {
    fun update()
    fun draw(gr: Graphics2D)
    fun handleInput()
}