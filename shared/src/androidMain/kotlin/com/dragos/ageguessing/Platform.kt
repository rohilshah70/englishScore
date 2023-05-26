package com.dragos.ageguessing

class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun getAge(age: Int): Int{
    return if (age < 7) age + 5 else age
}