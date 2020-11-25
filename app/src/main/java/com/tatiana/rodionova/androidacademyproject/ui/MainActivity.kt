package com.tatiana.rodionova.androidacademyproject.ui

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.tatiana.rodionova.androidacademyproject.R

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.navigationButton).setOnClickListener {
            startActivity(Intent(this, MovieDetailsActivity::class.java))
        }
    }
}
