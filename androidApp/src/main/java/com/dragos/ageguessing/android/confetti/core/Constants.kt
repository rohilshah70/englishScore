package com.dragos.ageguessing.android.confetti.core

import java.math.RoundingMode


const val PI = Math.PI
const val HALF_PI = PI / 2
const val TWO_PI = PI * 2
const val TWO_THIRD_PI = PI * 2 / 3
const val QUARTER_PI = PI / 4


fun Float.roundTo(n : Int) : Float {
    return this.toBigDecimal().setScale(n, RoundingMode.UP).toFloat()
}

enum class ConfettiType{
    SNOWFALL,
    EXPLOSION,
    METEOR,
    FOUNTAIN,
    CONFETTI;
}


