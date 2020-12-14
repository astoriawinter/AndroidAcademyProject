package com.tatiana.rodionova.androidacademyproject.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tatiana.rodionova.androidacademyproject.R
import com.tatiana.rodionova.androidacademyproject.ui.movies_list.MoviesListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.run {
            if (fragments.isEmpty()) {
                beginTransaction()
                    .replace(R.id.container, MoviesListFragment.newInstance())
                    .commit()
            }
        }
    }
}
