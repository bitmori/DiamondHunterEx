package me.kirkscope.game.hud

import me.kirkscope.game.GamePanel
import me.kirkscope.game.entity.Player
import me.kirkscope.game.manager.Content
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D

/**
 * KY |> Jul/2017
 */
class Hud(val player: Player, var numDiamonds: Int) {
    companion object {
        val BAR = Content.BAR[0][0]
        val DIAMOND = Content.DIAMOND[0][0]
        val BOAT = Content.ITEMS[0][0]
        val AXE = Content.ITEMS[0][1]
        val FONT = Font("Arial", Font.PLAIN, 10)
        val TEXT_COLOR = Color(47, 64, 126)
    }

    val yOffset = GamePanel.HEIGHT
}

fun Graphics2D.draw(h: Hud) {
    // hud
    val yOffset = h.yOffset
    this.drawImage(Hud.BAR, 0, yOffset, null)

    // diamond bar
    this.color = Hud.TEXT_COLOR
    this.fillRect(8, yOffset + 6, (28.0 * h.player.numDiamonds / h.numDiamonds).toInt(), 4)

    // diamond amount
    this.color = Hud.TEXT_COLOR
    this.font = Hud.FONT
    val text = "${h.player.numDiamonds}/${h.numDiamonds}"
    Content.drawString(this, text, 40, yOffset + 3)
    this.drawImage(Hud.DIAMOND, if (h.player.numDiamonds >= 10) 80 else 72, yOffset, null)

    // items
    if (h.player.hasBoat) {
        this.drawImage(Hud.BOAT, 100, yOffset, null)
    }
    if (h.player.hasAxe) {
        this.drawImage(Hud.AXE, 112, yOffset, null)
    }

    // time
    val minutes = h.player.ticks / 1800
    val seconds = (h.player.ticks / 30) % 60
    val timeString = "%02d:%02d".format(minutes, seconds)
    Content.drawString(this, timeString, 85, 3)
}