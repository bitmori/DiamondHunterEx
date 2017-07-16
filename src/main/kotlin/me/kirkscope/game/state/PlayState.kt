package me.kirkscope.game.state

import me.kirkscope.game.GamePanel
import me.kirkscope.game.entity.*
import me.kirkscope.game.hud.Hud
import me.kirkscope.game.hud.draw
import me.kirkscope.game.manager.JukeBox
import me.kirkscope.game.manager.KeyManager
import me.kirkscope.game.manager.StateManager
import me.kirkscope.game.tilemap.TileMap
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Rectangle

/**
 * Created by Kirk on 7/15/17.
 */
class PlayState(val stateManager: StateManager): IState {


    val tileMap = TileMap.of(16, "/Tilesets/testtileset.gif", "/Maps/testmap.map")
    val player = Player(tileMap)


    val diamonds = generateDiamonds()
    val items = generateItems()
    val sparkles = arrayListOf<Sparkle>()
    val boxes = arrayListOf<Rectangle>()

    val hud = Hud(player, diamonds.size)
    // camera pos
    val sectorSize = GamePanel.WIDTH
    var xSector = player.x / sectorSize
    var ySector = player.y / sectorSize

    var eventTick = 0
    var eventStart = true
    var eventFinish = false
    var blockInput = false

    init {
        player.setTilePosition(17, 17)
        player.totalDiamonds = diamonds.size
        tileMap.setPositionImmediately(-xSector * sectorSize, -ySector * sectorSize)

        // load music
        JukeBox.load("/Music/bgmusic.mp3", "music1")
        JukeBox.setVolume("music1", -10f)
        JukeBox.loop("music1", 1000, 1000, JukeBox.getFrames("music1") - 1000)
        JukeBox.load("/Music/finish.mp3", "finish")
        JukeBox.setVolume("finish", -10f)

        // load sfx
        JukeBox.load("/SFX/collect.wav", "collect")
        JukeBox.load("/SFX/mapmove.wav", "mapmove")
        JukeBox.load("/SFX/tilechange.wav", "tilechange")
        JukeBox.load("/SFX/splash.wav", "splash")

        onEventStart()
    }

    override fun update() {
        handleInput()

        if (eventStart) onEventStart()
        if (eventFinish) onEventFinish()

        if (player.numDiamonds == player.totalDiamonds) {
            eventFinish = true
            blockInput = true
        }

        // update camera
        val prevX = xSector
        val prevY = ySector

        xSector = player.x / sectorSize
        ySector = player.y / sectorSize
        tileMap.setPosition(-xSector * sectorSize, -ySector * sectorSize)
        tileMap.update()

        if (prevX != xSector || prevY != ySector) {
            JukeBox.play("mapmove")
        }

        if (tileMap.isMoving) {
            return
        }

        player.update()


        for (diamond in diamonds) {
            if (!diamond.valid) continue
            diamond.update()
            if (player.intersects(diamond)) {
                diamond.valid = false
                player.diamondCollected()

                JukeBox.play("collect")

                val sparkle = Sparkle(tileMap)
                sparkle.setPosition(diamond.x, diamond.y)
                sparkles.add(sparkle)

                for (change in diamond.tileChanges) {
                    tileMap.setTile(change[0], change[1], change[2])
                }
                if (!diamond.tileChanges.isEmpty()) {
                    JukeBox.play("tilechange")
                }
            }
        }

        for (sparkle in sparkles) {
            if (sparkle.valid) {
                sparkle.update()
            }
        }

        for (item in items) {
            if (!item.valid) continue
            if (player.intersects(item)) {
                item.valid = false
                item.onCollected(player)
                JukeBox.play("collect")
                val sparkle = Sparkle(tileMap)
                sparkle.setPosition(item.x, item.y)
                sparkles.add(sparkle)
            }
        }
    }

    override fun draw(gr: Graphics2D) {
        tileMap.drawOn(gr)

        player.drawOn(gr)

        diamonds.filter { it.valid }.forEach { it.drawOn(gr) }
        sparkles.filter { it.valid }.forEach { it.drawOn(gr) }
        items.filter { it.valid }.forEach { it.drawOn(gr) }

        gr.draw(hud)

        gr.color = Color.BLACK
        boxes.forEach { gr.fill(it) }


    }

    override fun handleInput() {
        if (KeyManager.isPressed(KeyManager.ESCAPE)) {
            JukeBox.stop("music1")
            stateManager.paused = true
        }
        if (blockInput) return
        when (true) {
            KeyManager.isDown(KeyManager.LEFT) -> player.direction = Direction.LEFT
            KeyManager.isDown(KeyManager.RIGHT) -> player.direction = Direction.RIGHT
            KeyManager.isDown(KeyManager.UP) -> player.direction = Direction.UP
            KeyManager.isDown(KeyManager.DOWN) -> player.direction = Direction.DOWN
            KeyManager.isPressed(KeyManager.SPACE) -> player.setAction()
        }

    }

    private fun generateDiamonds(): List<Diamond> {
        val d1 = Diamond(tileMap)
        d1.setTilePosition(20, 20)
        d1.addChange(intArrayOf(23, 19, 1))
        d1.addChange(intArrayOf(23, 20, 1))


        val d2 = Diamond(tileMap)
        d2.setTilePosition(12, 36)
        d2.addChange(intArrayOf(31, 17, 1))

        val d3 = Diamond(tileMap)
        d3.setTilePosition(28, 4)
        d3.addChange(intArrayOf(27, 7, 1))
        d3.addChange(intArrayOf(28, 7, 1))

        val d4 = Diamond(tileMap)
        d4.setTilePosition(4, 34)
        d4.addChange(intArrayOf(31, 21, 1))


        val d5 = Diamond(tileMap)
        d5.setTilePosition(28, 19)

        val d6 = Diamond(tileMap)
        d6.setTilePosition(35, 26)

        val d7 = Diamond(tileMap)
        d7.setTilePosition(38, 36)

        val d8 = Diamond(tileMap)
        d8.setTilePosition(27, 28)

        val d9 = Diamond(tileMap)
        d9.setTilePosition(20, 30)

        val d10 = Diamond(tileMap)
        d10.setTilePosition(14, 25)

        val d11 = Diamond(tileMap)
        d11.setTilePosition(4, 21)

        val d12 = Diamond(tileMap)
        d12.setTilePosition(9, 14)

        val d13 = Diamond(tileMap)
        d13.setTilePosition(4, 3)

        val d14 = Diamond(tileMap)
        d14.setTilePosition(20, 14)

        val d15 = Diamond(tileMap)
        d15.setTilePosition(13, 20)

        return listOf(d1, d2, d3, d4, d5, d6, d7, d8, d9, d10, d11, d12, d13, d14, d15)
    }

    private fun generateItems(): List<Item> {
        val it1 = Item(tileMap)
        it1.type = Item.AXE
        it1.setTilePosition(26, 37)

        val it2 = Item(tileMap)
        it2.type = Item.BOAT
        it2.setTilePosition(12, 4)

        return listOf(it1, it2)
    }

    private fun onEventStart() = when (++eventTick) {
        1 -> {
            boxes.clear()
            (0..8).forEach { i -> boxes.add(Rectangle(0, i * 16, GamePanel.WIDTH, 16)) }
        }
        in 2..31 -> boxes.forEachIndexed { i, r -> if (i % 2 == 0) r.x -= 4 else r.x += 4 }
        33 -> {
            boxes.clear()
            eventStart = false
            eventTick = 0
        }
        else -> { }
    }

    private fun onEventFinish() = when (++eventTick) {
        1 -> {
            boxes.clear()
            (0..8).forEach { i -> boxes.add(Rectangle(if (i % 2 == 0) -128 else 128, i * 16, GamePanel.WIDTH, 16)) }
            JukeBox.stop("music1")
            JukeBox.play("finish")
        }
        else -> {
            boxes.forEachIndexed { i, r -> if (i % 2 == 0 && r.x < 0) r.x += 4 else if (i % 2 != 0 && r.x > 0) r.x -= 4 }
            if (eventTick > 33 && !JukeBox.isPlaying("finish")) {
                StateManager.PLAY_TIME = player.ticks
                stateManager.state = StateManager.GAMEOVER
            } else {}
        }
    }
}