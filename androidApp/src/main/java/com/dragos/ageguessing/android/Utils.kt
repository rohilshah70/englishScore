package com.dragos.ageguessing.android

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

fun getConfettiImageList(context: Context): List<Bitmap> {
    //List of confetti images
    return listOf(
        BitmapFactory.decodeResource(
            context.resources,
            R.drawable.confetti1
        ),
        BitmapFactory.decodeResource(
            context.resources,
            R.drawable.confetti2
        ),
        BitmapFactory.decodeResource(
            context.resources,
            R.drawable.confetti3
        ),
        BitmapFactory.decodeResource(
            context.resources,
            R.drawable.confetti4
        ),
        BitmapFactory.decodeResource(
            context.resources,
            R.drawable.confetti5
        )
    )
}