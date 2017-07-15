package me.kirkscope.game.state

import me.kirkscope.game.manager.Content
import me.kirkscope.game.manager.JukeBox
import me.kirkscope.game.manager.KeyManager
import me.kirkscope.game.manager.StateManager
import java.awt.Graphics2D
import kotlin.system.exitProcess

/**
 * Created by Kirk on 7/15/17.
 */
class MenuState(val stateManager: StateManager): IState {
    companion object {
        val BG = Content.MENUBG[0][0]
        val DIAMOND = Content.DIAMOND[0][0]
        val OPTIONS = arrayOf("START", "QUIT")
        val LINE_HEIGHT = 10
    }

    init {
        JukeBox.load("/SFX/collect.wav", "collect")
        JukeBox.load("/SFX/menuoption.wav", "menuoption")
    }

    var currentOption = 0

    override fun update() {
        handleInput()
    }

    override fun draw(gr: Graphics2D) {
        gr.drawImage(BG, 0, 0, null)

        Content.drawString(gr, OPTIONS[0], 44, 90)
        Content.drawString(gr, OPTIONS[1], 48, 100)

        gr.drawImage(DIAMOND, 25, 86 + LINE_HEIGHT * currentOption, null)
    }

    override fun handleInput() {
        if (KeyManager.isPressed(KeyManager.DOWN) && currentOption < OPTIONS.size - 1) {
            JukeBox.play("menuoption")
            currentOption++
        }
        if (KeyManager.isPressed(KeyManager.UP) && currentOption > 0) {
            JukeBox.play("menuoption")
            currentOption--
        }
        if (KeyManager.isPressed(KeyManager.ENTER)) {
            JukeBox.play("collect")
            executeSelection()
        }
    }

     private fun executeSelection() = when (currentOption) {
         0 -> stateManager.state = StateManager.PLAY
         else -> exitProcess(1)
     }

}