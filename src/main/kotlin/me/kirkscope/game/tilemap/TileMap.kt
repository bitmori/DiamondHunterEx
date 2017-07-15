package me.kirkscope.game.tilemap

import me.kirkscope.game.GamePanel
import me.kirkscope.game.clamp
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.imageio.ImageIO


/**
 * Created by Kirk on 7/14/17.
 */
class Tile(val image: BufferedImage, val type: Int) {
    companion object {
        val NORMAL:Int = 0
        val BLOCKED:Int = 1
    }
}

class TileMap(val tileSize: Int, val tiles: Array<Array<Tile>>, val map: Array<IntArray>, val numTilesAcross: Int, val numRows: Int, val numCols: Int) {
    companion object {
        val MAP_DELIMS = "\\s+".toRegex()
        fun of(tileSize: Int, imageName: String, mapName: String): TileMap {
            val tileSet = ImageIO.read(javaClass.getResourceAsStream(imageName))
            val numTilesAcross = tileSet.width / tileSize
            val tiles = arrayOf(
                    Array(numTilesAcross) { col -> Tile(tileSet.getSubimage(col * tileSize, 0, tileSize, tileSize), Tile.NORMAL) },
                    Array(numTilesAcross) { col -> Tile(tileSet.getSubimage(col * tileSize, tileSize, tileSize, tileSize), Tile.BLOCKED) }
            )

            val input = javaClass.getResourceAsStream(mapName)
            val reader: BufferedReader = BufferedReader(InputStreamReader(input))
            val numCols = reader.readLine().toInt()
            val numRows = reader.readLine().toInt()

            val map = Array(numRows) {
                val line: String = reader.readLine()
                val tokens: Array<String> = line.split(MAP_DELIMS).dropLastWhile({ it.isEmpty() }).toTypedArray()
                IntArray(numCols) { col -> tokens[col].toInt() }
            }
            return TileMap(tileSize, tiles, map, numTilesAcross, numRows, numCols)
        }
    }
    // drawing
    val numRowsToDraw = GamePanel.HEIGHT/ tileSize + 2
    val numColsToDraw = GamePanel.WIDTH/ tileSize + 2
    val speed = 4
    var x = 0
    var y = 0
    val width = numCols * tileSize
    val height = numRows * tileSize
    val xMin = -width
    val xMax = 0
    val yMin = -height
    val yMax = 0

    var isMoving = false

    var xDest = 0
    var yDest = 0
    var colOffset = 0
    var rowOffset = 0

    fun getType(row: Int, col: Int): Int {
        val rc = map[row][col]
        val r = rc / numTilesAcross
        val c = rc % numTilesAcross
        return tiles[r][c].type
    }

    fun getIndex(row: Int, col: Int): Int {
        return map[row][col]
    }

    fun setTile(row: Int, col: Int, idx: Int) {
        map[row][col] = idx
    }

    fun replace(i1: Int, i2: Int) {
        for (row in 0 .. numRows - 1) {
            for (col in 0 .. numCols - 1) {
                if (map[row][col] == i1) {
                    map[row][col] = i2
                }
            }
        }
    }

    fun setPosition(x: Int, y: Int) {
        xDest = x
        yDest = y
    }

    fun setPositionImmediately(x: Int, y: Int) {
        this.x = x
        this.y = y
    }

    fun fixBounds() {
        x = x.clamp(xMin, xMax)
        y = y.clamp(yMin, yMax)
    }

    fun update() {
        if (x < xDest) {
            x += speed
            if (x > xDest) {
                x = xDest
            }
        }
        if (x > xDest) {
            x -= speed
            if (x < xDest) {
                x = xDest
            }
        }
        if (y < yDest) {
            y += speed
            if (y > yDest) {
                y = yDest
            }
        }
        if (y > yDest) {
            y -= speed
            if (y < yDest) {
                y = yDest
            }
        }

        fixBounds()

        colOffset = -this.x / tileSize
        rowOffset = -this.y / tileSize

        isMoving = (x != xDest || y != yDest)
    }

    fun drawOn(gr: Graphics2D) {
        for (row in rowOffset .. minOf(rowOffset + numRowsToDraw, numRows) - 1) {
            for (col in colOffset .. minOf(colOffset + numColsToDraw, numCols) - 1) {
                if (map[row][col] != 0) {
                    val rc = map[row][col]
                    val r = rc / numTilesAcross
                    val c = rc % numTilesAcross
                    gr.drawImage(tiles[r][c].image, x + col * tileSize, y+row*tileSize, null)
                }
            }
        }
    }
}

