package me.kirkscope.game

import javax.swing.JFrame

/**
 * KY |> Jul/2017
 */
fun main(args: Array<String>) {
    val window = JFrame("Diamond Hunter")

    window.add(GamePanel())

    window.isResizable = false
    window.pack()

    window.setLocationRelativeTo(null)
    window.isVisible = true
    window.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
}