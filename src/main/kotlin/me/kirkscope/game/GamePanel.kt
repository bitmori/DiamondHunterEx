package me.kirkscope.game

import me.kirkscope.game.manager.KeyManager
import me.kirkscope.game.manager.StateManager
import java.awt.Dimension
import java.awt.Graphics2D
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.image.BufferedImage
import javax.swing.JPanel

/**
 * Created by Kirk on 7/14/17.
 */
class GamePanel: JPanel(), Runnable, KeyListener {
    companion object {
        val WIDTH = 128
        val HEIGHT = 128
        val HEIGHT2 = HEIGHT + 16
        val SCALE = 3
        val FPS = 30
        val TARGET_TIME = 1000L / FPS
    }

    var thread: Thread? = null
    var running = false
    val image = BufferedImage(WIDTH, HEIGHT2, 1)
    val gr:Graphics2D = image.graphics as Graphics2D
    val stateManager = StateManager()

    init {
        preferredSize = Dimension(WIDTH * SCALE, HEIGHT2 * SCALE)
        isFocusable = true
        requestFocus()
    }

    override fun addNotify() {
        super.addNotify()
        if (thread == null) {
            addKeyListener(this)
            thread = Thread(this)
            thread!!.start()
        }
    }

    override fun keyTyped(e: KeyEvent?) {}

    override fun keyPressed(k: KeyEvent?) = KeyManager.keySet(k!!.keyCode, true)

    override fun keyReleased(k: KeyEvent?) = KeyManager.keySet(k!!.keyCode, false)

    override fun run() {
        running = true

        while (running) {
            val start = System.nanoTime()

            update()
            draw()
            drawToScreen()

            val elapsed = System.nanoTime() - start
            var wait = TARGET_TIME - elapsed/ 1000000
            if (wait < 0) {
                wait = TARGET_TIME
            }

            try {
                Thread.sleep(wait)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    private fun update() {
        stateManager.update()
        KeyManager.update()
    }

    private fun draw() {
        stateManager.draw(gr)
    }

    private fun drawToScreen() {
        graphics.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT2 * SCALE, null)
        graphics.dispose()
    }
}