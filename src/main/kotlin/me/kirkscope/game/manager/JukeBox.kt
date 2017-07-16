package me.kirkscope.game.manager

import java.io.BufferedInputStream
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import javax.sound.sampled.FloatControl

/**
 * Created by Kirk on 7/15/17.
 */
object JukeBox {
    val clips = hashMapOf<String, Clip>()
    val gap = 0

    fun load(path: String, name: String) {
        if (clips.contains(name)) {
            return
        }
        try {
            val input = AudioSystem.getAudioInputStream(BufferedInputStream(javaClass.getResourceAsStream(path)))
            val baseFormat = input.format
            val decodeFormat = AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.sampleRate, 16, baseFormat.channels, baseFormat.channels * 2, baseFormat.sampleRate, false)
            val decodedInput = AudioSystem.getAudioInputStream(decodeFormat, input)
            val clip = AudioSystem.getClip()
            clip.open(decodedInput)
            clips.put(name, clip)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun play(name: String) {
        val clip = clips[name] ?: return
        if (clip.isRunning) {
            clip.stop()
        }
        clip.framePosition = gap
        while (!clip.isRunning()) {
            clip.start()
        }
    }


    fun stop(name: String) {
        val clip = clips[name] ?: return
        if (clip.isRunning) {
            clip.stop()
        }
    }

    fun resumeLoop(s: String) {
        val clip = clips[s] ?: return
        clip.loop(Clip.LOOP_CONTINUOUSLY)
    }

    fun loop(name: String, frame: Int, start: Int, end: Int) {
        val clip = clips[name] ?: return
        if (clip.isRunning) {
            clip.stop()
        }
        clip.setLoopPoints(start, end)
        clip.framePosition = frame
        clip.loop(Clip.LOOP_CONTINUOUSLY)
    }

    fun getFrames(s: String): Int {
        return clips[s]?.frameLength ?:0
    }

    fun setVolume(s: String, f: Float) {
        val clip = clips[s] ?: return
        val volume = clip.getControl(FloatControl.Type.MASTER_GAIN) as? FloatControl
        volume?.value = f
    }

    fun isPlaying(s: String): Boolean {
        return clips[s]?.isRunning ?: false
    }
}