package me.kirkscope.game.entity

import me.kirkscope.game.manager.Content
import me.kirkscope.game.tilemap.TileMap
import java.awt.Graphics2D

/**
 * Created by Kirk on 7/15/17.
 */
class Item(tileMap: TileMap): Entity(tileMap) {
    companion object {
        val BOAT = 0
        val AXE = 1
    }
    var type = BOAT

    fun onCollected(p: Player) {
        when (type) {
            BOAT -> p.hasBoat = true
            AXE -> p.hasAxe = true
        }
    }

    override fun update() {
        if (isMoving) getNextPosition()

        if (x == xDest && y == yDest) {
            direction = Direction.STOP
            isMoving = false
            rowTile = y / tileMap.tileSize
            colTile = x / tileMap.tileSize
        }

        animation.update()
    }

    override fun drawOn(gr: Graphics2D) {
        gr.drawImage(Content.ITEMS[1][type], x + tileMap.x - width / 2, y + tileMap.y - height / 2, null)
    }
}