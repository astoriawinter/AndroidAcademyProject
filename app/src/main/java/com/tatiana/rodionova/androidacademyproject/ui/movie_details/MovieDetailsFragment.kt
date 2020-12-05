package com.tatiana.rodionova.androidacademyproject.ui.movie_details

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tatiana.rodionova.androidacademyproject.R
import com.tatiana.rodionova.androidacademyproject.decorator.ItemDecorator
import com.tatiana.rodionova.androidacademyproject.model.Actor
import com.tatiana.rodionova.androidacademyproject.model.Movie
import com.tatiana.rodionova.androidacademyproject.ui.movie_details.adapter.ActorAdapter
import com.tatiana.rodionova.androidacademyproject.utils.setGradient
import com.tatiana.rodionova.androidacademyproject.utils.withArgs

class MovieDetailsFragment : Fragment(R.layout.fragment_movies_details) {

    override fun onAttach(context: Context) {
        super.onAttach(context)

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    fragmentManager?.run {
                        if (backStackEntryCount > 0) {
                            popBackStackImmediate()
                        }
                    }
                }
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.run {
            arguments?.getParcelable<Movie>(MOVIE_ARG)?.let { movie -> initMovieDetail(movie) }

            findViewById<TextView>(R.id.backTextView).setOnClickListener { requireActivity().onBackPressed() }

            val gradientViews = listOf<TextView>(
                findViewById(R.id.cast),
                findViewById(R.id.storyLine),
                findViewById(R.id.movieTitle)
            )
            gradientViews.forEach(TextView::setGradient)
        }
    }

    private fun View.initMovieDetail(movie: Movie) {
        findViewById<RecyclerView>(R.id.actorsRecyclerView).run {
            addItemDecoration(ItemDecorator(R.dimen.actor_offset, spanCount))
            adapter = ActorAdapter().apply { list = movie.actors }
            layoutManager = GridLayoutManager(context, spanCount, GridLayoutManager.VERTICAL, false)
        }
        val image = findViewById<ImageView>(R.id.movieImage)
        image.setImageDrawable(ContextCompat.getDrawable(requireContext(), movie.backgroundPoster))
        findViewById<TextView>(R.id.movieTitle).text = movie.name
        findViewById<TextView>(R.id.storyLineLabel).text = movie.description
        findViewById<TextView>(R.id.reviewsNumber).text = context.getString(R.string.reviews_number, movie.reviewNumber)
        findViewById<TextView>(R.id.movieGenres).text = movie.genres.joinToString(separator = ", ")
        findViewById<RatingBar>(R.id.ratingBar).rating = movie.rating
    }

    companion object {

        const val MOVIE_ARG = "movie arg"
        private const val spanCount = 4

        fun newInstance(movie: Movie) = MovieDetailsFragment().withArgs {
            putParcelable(MOVIE_ARG, movie)
        }
    }
}