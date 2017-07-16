package me.kirkscope.game.entity

import me.kirkscope.game.manager.Content
import me.kirkscope.game.manager.JukeBox
import me.kirkscope.game.tilemap.TileMap

/**
 * Created by Kirk on 7/15/17.
 */
class Player(tileMap: TileMap): Entity(tileMap) {
    companion object {
        val DOWN = 0
        val LEFT = 1
        val RIGHT = 2
        val UP = 3
        val DOWN_BOAT = 4
        val LEFT_BOAT = 5
        val RIGHT_BOAT = 6
        val UP_BOAT = 7
    }

    override var speed = 2
    var numDiamonds = 0
    var totalDiamonds = 0
    var currentAnimation = 0
      set(value) {
          field = value
          animation.frames = Content.PLAYER[value]
          animation.delay = 10
      }

    init {
        currentAnimation = 0
    }

    var ticks = 0L
    var onWater = false

    fun diamondCollected() {
        numDiamonds++
    }


    var hasBoat = false
        set(value) {
            field = value
            if (value) {
                tileMap.replace(22, 4)
            }
        }
    var hasAxe = false

    fun setAction() {
        if (!hasAxe) return
        when (currentAnimation) {
            UP -> if (tileMap.getIndex(rowTile - 1, colTile) == 21) {
                tileMap.setTile(rowTile - 1, colTile, 1)
                JukeBox.play("tilechange")
            }
            DOWN -> if (tileMap.getIndex(rowTile + 1, colTile) == 21) {
                tileMap.setTile(rowTile + 1, colTile, 1)
                JukeBox.play("tilechange")
            }
            LEFT -> if (tileMap.getIndex(rowTile, colTile - 1) == 21) {
                tileMap.setTile(rowTile, colTile - 1, 1)
                JukeBox.play("tilechange")
            }
            RIGHT -> if (tileMap.getIndex(rowTile, colTile + 1) == 21) {
                tileMap.setTile(rowTile, colTile + 1, 1)
                JukeBox.play("tilechange")
            }
            else -> {}
        }
    }

    override fun update() {
        ticks++

        // check if on water
        val current = onWater
        onWater = 4 == tileMap.getIndex(yDest / tileMap.tileSize, xDest / tileMap.tileSize)

        // if going from land to water
        if (!current && onWater) {
            JukeBox.play("splash")
        }

        when (direction) {
            Direction.DOWN -> if (onWater && currentAnimation != DOWN_BOAT) {
                currentAnimation = DOWN_BOAT
            } else if (!onWater && currentAnimation != DOWN) {
                currentAnimation = DOWN
            }
            Direction.LEFT -> if (onWater && currentAnimation != LEFT_BOAT) {
                currentAnimation = LEFT_BOAT
            } else if (!onWater && currentAnimation != LEFT) {
                currentAnimation = LEFT
            }
            Direction.RIGHT -> if (onWater && currentAnimation != RIGHT_BOAT) {
                currentAnimation = RIGHT_BOAT
            } else if (!onWater && currentAnimation != RIGHT) {
                currentAnimation = RIGHT
            }
            Direction.UP -> if (onWater && currentAnimation != UP_BOAT) {
                currentAnimation = UP_BOAT
            } else if (!onWater && currentAnimation != UP) {
                currentAnimation = UP
            }
            else -> {}
        }

        if (isMoving) getNextPosition()

        if (x == xDest && y == yDest) {
            direction = Direction.STOP
            isMoving = false
            rowTile = y / tileMap.tileSize
            colTile = x / tileMap.tileSize
        }

        animation.update()
    }
}