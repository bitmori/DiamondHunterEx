package me.kirkscope.game.entity

import me.kirkscope.game.manager.Content
import me.kirkscope.game.tilemap.TileMap

/**
 * Created by Kirk on 7/15/17.
 */
class Diamond(tileMap: TileMap): Entity(tileMap) {
    private val tileChanges: MutableList<IntArray> = mutableListOf()

    init {
        animation.frames = Content.DIAMOND[0]
        animation.delay = 10
    }

    fun addChange(change: IntArray) {
        tileChanges.add(change)
    }

    override fun update() {
        animation.update()
    }
}