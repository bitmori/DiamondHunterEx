package me.kirkscope.game.entity

import me.kirkscope.game.manager.Content
import me.kirkscope.game.tilemap.TileMap

/**
 * Created by Kirk on 7/15/17.
 */
class Sparkle(tileMap: TileMap) : Entity(tileMap) {

    private var remove: Boolean = false

    init {
        animation.frames = Content.SPARKLE[0]
        animation.delay = 5
    }

    override fun update() {
        animation.update()
        if (animation.hasPlayedOnce()) {
            remove = true
        }
    }
}