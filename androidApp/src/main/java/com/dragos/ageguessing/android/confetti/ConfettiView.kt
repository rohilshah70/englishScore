package com.dragos.ageguessing.android.confetti

import android.graphics.Bitmap
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.dragos.ageguessing.android.confetti.core.ConfettiType
import com.dragos.ageguessing.android.confetti.core.PI
import com.dragos.ageguessing.android.confetti.particle.*

@Composable
fun ConfettiView(
    type: ConfettiType = ConfettiType.SNOWFALL,
    imageList: List<Bitmap>,
    colorList: List<Color> = listOf(Color.Red)
) {
    when (type){
        ConfettiType.SNOWFALL -> {
            SnowFall(
                imageList = imageList,
                colorList = colorList
            )
        }
        ConfettiType.EXPLOSION -> {
            Explosion(
                imageList = imageList,
                colorList = colorList
            )
        }
        ConfettiType.METEOR -> {
            Meteor(
                imageList = imageList,
                colorList = colorList
            )
        }
        ConfettiType.FOUNTAIN -> {
            Fountain(
                imageList = imageList,
                colorList = colorList
            )
        }
        ConfettiType.CONFETTI ->{
            Confetti(
                imageList = imageList,
                colorList = colorList
            )
        }
    }
}

@Composable
fun Fountain(
    imageList: List<Bitmap>,
    colorList: List<Color>
) {
    CreateParticles(
        modifier = Modifier
            .fillMaxSize(),
        x = 500f, y = 2000f,
        velocity = Velocity(xDirection = 1f, yDirection = -15f, angle = PI, randomize = true),
        force = Force.Gravity(0.2f),
        acceleration = Acceleration(0f, -4f),
        lifeTime = LifeTime(255f, 1f),
        emissionType = EmissionType.FlowEmission(maxParticlesCount = 500),
        durationMillis = 10 * 1000,
        imageList = imageList,
        colorList = colorList
    )
}

@Composable
fun Confetti(
    imageList: List<Bitmap>,
    colorList: List<Color>
) {
    CreateParticles(
        modifier = Modifier
            .fillMaxSize(),
        x = 500f, y = 200f,
        velocity = Velocity(xDirection = 2f, yDirection = -2f, randomize = true),
        force = Force.Gravity(0.3f),
        acceleration = Acceleration(),
        lifeTime = LifeTime(255f, 2f),
        emissionType = EmissionType.FlowEmission(
            maxParticlesCount = EmissionType.FlowEmission.INDEFINITE,
            emissionRate = 0.8f
        ),
        durationMillis = 10 * 1000,
        imageList = imageList,
        colorList = colorList
    )
}


@Composable
fun Meteor(
    imageList: List<Bitmap>,
    colorList: List<Color>
) {
    CreateParticles(
        modifier = Modifier
            .fillMaxSize(),
        x = 500f, y = 1200f,
        velocity = Velocity(xDirection = 1f, yDirection = 1f, randomize = true),
        force = Force.Wind(-0.2f, -0.1f),
        acceleration = Acceleration(-1f, -2f),
        lifeTime = LifeTime(255f, 6f),
        emissionType = EmissionType.FlowEmission(
            maxParticlesCount = EmissionType.FlowEmission.INDEFINITE,
            emissionRate = 1f
        ),
        durationMillis = 10 * 1000,
        imageList = imageList,
        colorList = colorList
    )
}

@Composable
fun Explosion(
    imageList: List<Bitmap>,
    colorList: List<Color>
) {
    CreateParticles(
        modifier = Modifier
            .fillMaxSize(),
        x = 500f, y = 500f,
        velocity = Velocity(xDirection = -4f, yDirection = 4f),
        force = Force.Gravity(0.15f),
        acceleration = Acceleration(0f, 0f),
        lifeTime = LifeTime(255f, 0.05f),
        emissionType = EmissionType.FlowEmission(maxParticlesCount = 30, emissionRate = 1f),
        durationMillis = 3 * 1000,
        imageList = imageList,
        colorList = colorList
    )
}

@Composable
fun SnowFall(
    imageList: List<Bitmap>,
    colorList: List<Color>
) {
    CreateParticles(
        modifier = Modifier
            .fillMaxSize(),
        x = 500f, y = -250f,
        velocity = Velocity(xDirection = 7f, yDirection = 7f, randomize = true),
        force = Force.Gravity(0.4f),
        acceleration = Acceleration(),
        lifeTime = LifeTime(255f, 0.01f),
        emissionType = EmissionType.FlowEmission(maxParticlesCount = 100, emissionRate = 1f),
        durationMillis = 3 * 1000,
        imageList = imageList,
        colorList = colorList
    )
}