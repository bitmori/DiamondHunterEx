package me.kirkscope.game.entity

import java.awt.image.BufferedImage

/**
 * Created by Kirk on 7/15/17.
 */
class Animation {
    var currentFrame: Int = 0
    var numFrames: Int = 0
    var count: Int = 0
    var delay: Int = 0
    var timesPlayed: Int = 0
    var frames: Array<BufferedImage> = emptyArray()
        set(f) {
            field = f
            currentFrame = 0
            count = 0
            timesPlayed = 0
            delay = 2
            numFrames = f.size
        }

    fun update() {
        if (delay == -1) return
        count++
        if (count == delay) {
            currentFrame++
            count = 0
        }
        if (currentFrame == numFrames) {
            currentFrame = 0
            timesPlayed++
        }
    }

    fun getImage(): BufferedImage = frames[currentFrame]

    fun hasPlayedOnce()= timesPlayed > 0
    fun hasPlayed(i: Int) = timesPlayed == i
}