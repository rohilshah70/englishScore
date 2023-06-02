package com.dragos.ageguessing.android.confetti.emitters

import androidx.compose.ui.graphics.drawscope.DrawScope
import com.dragos.ageguessing.android.confetti.core.Vector2D
import com.dragos.ageguessing.android.confetti.particle.Particle
import com.dragos.ageguessing.android.confetti.particle.ParticleConfigData
import com.dragos.ageguessing.android.confetti.particle.createAccelerationVector
import com.dragos.ageguessing.android.confetti.particle.createVelocityVector

internal abstract class Emitter(
    private val particleConfigData: ParticleConfigData
) {

    val particlePool = mutableListOf<Particle>()

    abstract fun generateParticles(numberOfParticles: Int)

    fun addParticle() {
        val particle = createFreshParticle()
        particlePool.add(particle)
    }

    private fun createFreshParticle(): Particle {
        return Particle(
            initialX = particleConfigData.x,
            initialY = particleConfigData.y,
            velocity = particleConfigData.velocity.createVelocityVector(),
            acceleration = particleConfigData.acceleration.createAccelerationVector(),
            lifetime = particleConfigData.lifeTime.maxLife,
            agingFactor = particleConfigData.lifeTime.agingFactor,
            imageList = particleConfigData.imageList,
            colorList = particleConfigData.colorList
        )
    }

    fun applyForce(force: Vector2D) {
        for (particle in particlePool) {
            particle.applyForce(force)
        }
    }

    abstract fun update(dt: Float)

    fun render(drawScope: DrawScope) {
        for (particle in particlePool) {
            particle.show(drawScope)
        }
    }
}
