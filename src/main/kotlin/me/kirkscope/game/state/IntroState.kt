package me.kirkscope.game.state

import me.kirkscope.game.GamePanel
import me.kirkscope.game.manager.KeyManager
import me.kirkscope.game.manager.StateManager
import java.awt.Color
import java.awt.Graphics2D
import javax.imageio.ImageIO

/**
 * Created by Kirk on 7/15/17.
 */
class IntroState(val stateManager: StateManager): IState {
    companion object {
        val FADE_IN = 60
        val LENGTH = 60
        val FADE_OUT = 60
    }

    val logo = ImageIO.read(javaClass.getResourceAsStream("/Logo/logo.gif"))

    var alpha = 0
    var ticks = 0

    override fun update() {
        handleInput()
        when (++ticks) {
            in 0..FADE_IN -> alpha = Math.max(0, (255 - 255 * (1.0 * ticks / FADE_IN)).toInt())
            in FADE_IN + 1..FADE_IN + LENGTH - 1 -> { }
            in FADE_IN + LENGTH..FADE_IN + LENGTH + FADE_OUT -> alpha = Math.min(255, (255 * (1.0 * ticks - FADE_IN.toDouble() - LENGTH.toDouble()) / FADE_OUT).toInt())
            else -> stateManager.state = StateManager.MENU
        }
    }

    override fun draw(gr: Graphics2D) {
        gr.color = Color.WHITE
        gr.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT2)
        gr.drawImage(logo, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT2, null)
        gr.color = Color(0, 0, 0, alpha)
        gr.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT2)
    }

    override fun handleInput() {
        if (KeyManager.isPressed(KeyManager.ENTER)) {
            stateManager.state = StateManager.MENU
        }
    }
}