package com.dragos.ageguessing.android.confetti.particle

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import com.dragos.ageguessing.android.confetti.core.TWO_PI
import com.dragos.ageguessing.android.confetti.core.Vector2D
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random


data class Velocity(val xDirection: Float = 0f, val yDirection: Float = 0f, val angle: Double = TWO_PI, val randomize: Boolean = true)

internal fun Velocity.createVelocityVector(): Vector2D {
    return if (this.randomize) {
        Vector2D(
            x = (this.xDirection * cos(angle * Random.nextFloat())).toFloat(),
            y = (this.yDirection * sin(angle * Random.nextFloat())).toFloat()
        )
    } else {
        Vector2D(
            x = (this.xDirection * cos(angle)).toFloat(),
            y = (this.yDirection * sin(angle)).toFloat()
        )
    }
}

data class Acceleration(val xComponent: Float = 0f, val yComponent: Float = 0f, val uniform: Boolean = false)

internal fun Acceleration.createAccelerationVector(): Vector2D {
    return if (!uniform) {
        Vector2D(xComponent * Random.nextFloat(), yComponent * Random.nextFloat())
    } else {
        Vector2D(this.xComponent, this.yComponent)
    }
}

sealed class Force {
    data class Gravity(val magnitude: Float = 0f) : Force()
    data class Wind(val xDirection: Float = 0f, val yDirection: Float = 0f) : Force()
}

internal fun Force.createForceVector(): Vector2D {
    return when (this) {
        is Force.Gravity -> {
            Vector2D(0f, this.magnitude)
        }
        is Force.Wind -> {
            Vector2D(this.xDirection, this.yDirection)
        }
    }
}

data class LifeTime(val maxLife: Float = 255f, val agingFactor: Float = 15f)

sealed class EmissionType {
    data class ExplodeEmission(
        val numberOfParticles: Int = 30
    ) : EmissionType()

    data class FlowEmission(
        val maxParticlesCount: Int = 50,
        val emissionRate: Float = 0.5f
    ) : EmissionType() {
        companion object {
            @JvmField
            val INDEFINITE = -2
        }
    }
}

internal data class ParticleConfigData(
    val x: Float = 0f,
    val y: Float = 0f,
    val velocity: Velocity,
    val force: Force,
    val acceleration: Acceleration,
    val lifeTime: LifeTime,
    val emissionType: EmissionType,
    val imageList: List<Bitmap>,
    val colorList: List<Color> = listOf(Color.Red)
)

