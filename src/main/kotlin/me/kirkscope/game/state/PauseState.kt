package me.kirkscope.game.state

import me.kirkscope.game.manager.Content
import me.kirkscope.game.manager.JukeBox
import me.kirkscope.game.manager.KeyManager
import me.kirkscope.game.manager.StateManager
import java.awt.Graphics2D

/**
 * Created by Kirk on 7/15/17.
 */
class PauseState(val stateManager: StateManager): IState {
    override fun update() {
        handleInput()
    }

    override fun draw(gr: Graphics2D) {
        Content.drawString(gr, "paused", 40, 30)

        Content.drawString(gr, "arrow", 12, 76)
        Content.drawString(gr, "keys", 16, 84)
        Content.drawString(gr, ": move", 52, 80)

        Content.drawString(gr, "space", 12, 96)
        Content.drawString(gr, ": action", 52, 96)

        Content.drawString(gr, "F1:", 36, 112)
        Content.drawString(gr, "return", 68, 108)
        Content.drawString(gr, "to menu", 68, 116)
    }

    override fun handleInput() {
        if (KeyManager.isPressed(KeyManager.ESCAPE)) {
            stateManager.paused = false
            JukeBox.resumeLoop("music1")
        }
        if (KeyManager.isPressed(KeyManager.F1)) {
            stateManager.paused = false
            stateManager.state = StateManager.MENU
        }
    }
}