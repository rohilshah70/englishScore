package com.dragos.ageguessing.android.confetti.particle

import androidx.compose.ui.graphics.drawscope.DrawScope
import com.dragos.ageguessing.android.confetti.core.Vector2D
import com.dragos.ageguessing.android.confetti.core.add
import com.dragos.ageguessing.android.confetti.core.roundTo
import com.dragos.ageguessing.android.confetti.core.scalarMultiply
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.asImageBitmap

import android.graphics.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter

internal class Particle constructor(
    var initialX: Float = 0f,
    var initialY: Float = 0f,
    var velocity: Vector2D = Vector2D(0f, 0f),
    var acceleration: Vector2D = Vector2D(0f, 0f),
    var lifetime: Float = 255f,
    var agingFactor: Float = 20f,
    var imageList: List<Bitmap>,
    var colorList: List<Color> = listOf(Color.Red)
) : Vector2D(initialX, initialY) {

    private val originalLife = lifetime
    private var alpha = 1f

    fun finished(): Boolean = this.lifetime < 0

    fun applyForce(force: Vector2D) {
        this.acceleration.add(force)
    }

    fun update(dt: Float) {
        lifetime -= agingFactor

        if (lifetime >= 0) {
            this.alpha = (lifetime / originalLife).roundTo(3)
        }

        // Add acceleration to velocity vector
        this.velocity.add(acceleration)

        // add velocity vector to positions
        this.add(velocity, scalar = dt)

        //set acceleration back to 0
        this.acceleration.scalarMultiply(0f)
    }

    fun show(drawScope: DrawScope) {
        drawScope.drawImage(
            image = imageList.random().asImageBitmap(),
            alpha = alpha,
            topLeft = Offset(x, y),
            colorFilter = ColorFilter.tint(colorList.random())
        )
    }
}
