package com.tatiana.rodionova.androidacademyproject.ui

import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.Shader.TileMode
import android.os.Bundle
import android.text.TextPaint
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tatiana.rodionova.androidacademyproject.R
import com.tatiana.rodionova.androidacademyproject.adapter.ActorAdapter
import com.tatiana.rodionova.androidacademyproject.adapter.ItemDecorator
import com.tatiana.rodionova.androidacademyproject.model.Actor

class MovieDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        findViewById<RecyclerView>(R.id.actorsRecyclerView).run {
            addItemDecoration(ItemDecorator(resources.getDimension(R.dimen.actor_offset).toInt()))
            adapter = ActorAdapter().apply { list = getActorData() }
            layoutManager = GridLayoutManager(context, 4, GridLayoutManager.VERTICAL, false)
        }
        findViewById<RatingBar>(R.id.ratingBar).rating = 3.5f
        findViewById<TextView>(R.id.cast).setGradient(
            intArrayOf(
                ContextCompat.getColor(applicationContext, R.color.gallery),
                ContextCompat.getColor(applicationContext, R.color.silver),
                ContextCompat.getColor(applicationContext, R.color.white)
            )
        )
    }

    private fun TextView.setGradient(colors: IntArray) {
        val paint: TextPaint = paint
        val width = paint.measureText(text.toString())

        val textShader: Shader = LinearGradient(
            0f, 0f, width, textSize,
            colors, floatArrayOf(0f, 0.47f, 1f), TileMode.CLAMP
        )
        paint.shader = textShader
    }

    private fun getActorData(): List<Actor> =
        listOf(
            Actor("Robert Downey Jr.", R.drawable.robert_downey),
            Actor("Chris Evans", R.drawable.chris_evans),
            Actor("Mark Ruffalo", R.drawable.mark_ruffalo),
            Actor("Chris Hemsworth", R.drawable.chris_hemsworth)
        )
}