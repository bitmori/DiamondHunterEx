package me.kirkscope.game.entity

import me.kirkscope.game.tilemap.Tile
import me.kirkscope.game.tilemap.TileMap
import java.awt.Graphics2D
import java.awt.Rectangle

/**
 * Created by Kirk on 7/15/17.
 */
enum class Direction {
    LEFT, RIGHT, UP, DOWN, STOP;
}

abstract class Entity(val tileMap: TileMap) {
    protected val animation: Animation = Animation()
    var x = 0
    var y = 0
    protected var xDest = 0
    protected var yDest = 0
    protected var isMoving = false
    protected var rowTile = 0
    protected var colTile = 0
    protected open var speed = 0

    var valid = true
    var width = 16
    var height = 16
    val cwidth = 12
    val cheight = 12

    var direction = Direction.LEFT
        set(dir) {
            if (!isMoving) {
                field = dir
                isMoving = validateNextPosition()
            }
        }

    fun setPosition(x: Int, y: Int) {
        this.x = x
        this.y = y
        xDest = x
        yDest = y
    }

    fun setTilePosition(row: Int, col: Int) {
        y = row * tileMap.tileSize + tileMap.tileSize / 2
        x = col * tileMap.tileSize + tileMap.tileSize / 2
        xDest = x
        yDest = y
    }

    fun intersects(o: Entity): Boolean {
        return getRectangle().intersects(o.getRectangle())
    }

    fun getRectangle(): Rectangle {
        return Rectangle(x, y, cwidth, cheight)
    }

    fun validateNextPosition(): Boolean {
        if (isMoving) return true
        rowTile = y / tileMap.tileSize
        colTile = x / tileMap.tileSize

        when (direction) {
            Direction.LEFT -> if(colTile == 0 || tileMap.getType(rowTile, colTile - 1) == Tile.BLOCKED) {
                return false
            }
            else {
                xDest = x - tileMap.tileSize
            }
            Direction.RIGHT -> if(colTile == tileMap.numCols || tileMap.getType(rowTile, colTile + 1) == Tile.BLOCKED) {
                return false
            }
            else {
                xDest = x + tileMap.tileSize
            }
            Direction.UP -> if(rowTile == 0 || tileMap.getType(rowTile - 1, colTile) == Tile.BLOCKED) {
                return false
            }
            else {
                yDest = y - tileMap.tileSize
            }
            Direction.DOWN -> if(rowTile == tileMap.numRows - 1 || tileMap.getType(rowTile + 1, colTile) == Tile.BLOCKED) {
                return false
            }
            else {
                yDest = y + tileMap.tileSize
            }
            else -> return true
        }
        return true
    }

    fun getNextPosition() {
        when (direction) {
            Direction.LEFT -> if (x > xDest) x -= speed else {direction = Direction.STOP; x = xDest}
            Direction.RIGHT -> if (x < xDest) x += speed else {direction = Direction.STOP; x = xDest}
            Direction.UP -> if (y > yDest) y -= speed else {direction = Direction.STOP; y = yDest}
            Direction.DOWN -> if (y < yDest) y += speed else {direction = Direction.STOP; y = yDest}
            else -> {}
        }
    }

    abstract fun update()

    fun Graphics2D.draw(entity: Entity) {
        entity.drawOn(this)
    }

    open fun drawOn(gr: Graphics2D) {
        gr.drawImage(animation.getImage(), x + tileMap.x - width / 2, y + tileMap.y - height / 2, null)
    }
}