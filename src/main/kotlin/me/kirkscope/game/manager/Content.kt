package me.kirkscope.game.manager

import java.awt.Graphics2D
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import kotlin.system.exitProcess

/**
 * Created by Kirk on 7/15/17.
 */
object Content {
    val MENUBG = load("/HUD/menuscreen.gif", 128, 144)
    val BAR = load("/HUD/bar.gif", 128, 16)

    val PLAYER = load("/Sprites/playersprites.gif", 16, 16)
    val DIAMOND = load("/Sprites/diamond.gif", 16, 16)
    val SPARKLE = load("/Sprites/sparkle.gif", 16, 16)
    val ITEMS = load("/Sprites/items.gif", 16, 16)
    val FONT = load("/HUD/font.gif", 8, 8)

    fun load(name: String, w: Int, h: Int): Array<Array<BufferedImage>> {
        try {
            val spriteSheet = ImageIO.read(javaClass.getResourceAsStream(name))
            val width = spriteSheet.width / w
            val height = spriteSheet.height / h
            return Array(height) { row -> Array(width) { col -> spriteSheet.getSubimage(col * w, row * h, w, h) } }
        } catch (e: Exception) {
            e.printStackTrace()
            println("Error loading graphics.")
            exitProcess(1)
        }
    }

    fun drawString(gr: Graphics2D, str: String, x: Int, y: Int) {
        for ((i, c) in str.toUpperCase().withIndex()) {
            val ch = when (c.toInt()) {
                47 -> 36 // slash
                58 -> 37 // colon
                32 -> 38 // space
                in 65..90 -> c.toInt() - 65 // letters
                in 48..57 -> c.toInt() - 22 // numbers
                else -> c.toInt()
            }
            val row = ch / FONT[0].size
            val col = ch % FONT[0].size
            gr.drawImage(FONT[row][col], x + 8 * i, y, null)
        }
    }
}