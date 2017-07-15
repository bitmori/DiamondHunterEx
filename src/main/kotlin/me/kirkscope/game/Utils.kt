package me.kirkscope.game

/**
 * Created by Kirk on 7/15/17.
 */
fun Int.clamp(min: Int, max: Int): Int = Math.max(min, Math.min(this, max))
