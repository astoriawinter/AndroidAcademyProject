package com.tatiana.rodionova.androidacademyproject.utils

import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.text.TextPaint
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.tatiana.rodionova.androidacademyproject.R

fun TextView.setGradient(
        colors: IntArray = intArrayOf(
                ContextCompat.getColor(context, R.color.gallery),
                ContextCompat.getColor(context, R.color.silver),
                ContextCompat.getColor(context, R.color.white))
) {
    val paint: TextPaint = paint
    val width = paint.measureText(text.toString())

    val textShader: Shader = LinearGradient(
            0f, 0f, width, textSize,
            colors, floatArrayOf(0f, 0.47f, 1f), Shader.TileMode.CLAMP
    )
    paint.shader = textShader
}

inline fun <T : Fragment> T.withArgs(argsBuilder: Bundle.() -> Unit): T =
        this.apply {
            arguments = Bundle().apply(argsBuilder)
        }