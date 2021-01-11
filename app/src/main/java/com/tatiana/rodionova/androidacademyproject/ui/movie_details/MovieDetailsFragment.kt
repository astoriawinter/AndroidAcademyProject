package com.tatiana.rodionova.androidacademyproject.ui.movie_details

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tatiana.rodionova.androidacademyproject.R
import com.tatiana.rodionova.androidacademyproject.decorator.ItemDecorator
import com.tatiana.rodionova.androidacademyproject.model.Movie
import com.tatiana.rodionova.androidacademyproject.model.Movie.Companion.calculateRating
import com.tatiana.rodionova.androidacademyproject.model.Movie.Companion.split
import com.tatiana.rodionova.androidacademyproject.ui.movie_details.adapter.ActorAdapter
import com.tatiana.rodionova.androidacademyproject.ui.movies_list.MovieListState
import com.tatiana.rodionova.androidacademyproject.utils.setGradient
import com.tatiana.rodionova.androidacademyproject.utils.withArgs
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsFragment : Fragment(R.layout.fragment_movies_details) {
    private val model: MovieDetailsViewModel by viewModel()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    parentFragmentManager.run {
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
            val id = arguments?.getLong(MOVIE_ID_ARG) ?: 0
            model.getMovieById(id).observe(viewLifecycleOwner, { state ->
                when (state) {
                    is MovieDetailsState.Loading -> renderUIState(
                        view = view,
                        isLoading = true,
                        isError = false
                    )
                    is MovieDetailsState.Error -> renderUIState(view = view, isLoading = true, isError = true)
                    is MovieDetailsState.Success -> {
                        renderUIState(view = view, isLoading = false, isError = false)
                        state.movie?.let { initMovieDetail(it) }
                    }
                }
            })

            findViewById<TextView>(R.id.backTextView).setOnClickListener { requireActivity().onBackPressed() }

            val gradientViews = listOf<TextView>(
                findViewById(R.id.cast),
                findViewById(R.id.storyLine),
                findViewById(R.id.movieTitle)
            )
            gradientViews.forEach(TextView::setGradient)
        }
    }

    private fun renderUIState(view: View, isLoading: Boolean, isError: Boolean) = with(view) {
        val root = findViewById<NestedScrollView>(R.id.root)
        val loading = findViewById<ProgressBar>(R.id.loading)
        val error = findViewById<TextView>(R.id.error)

        error.isVisible = isError && !isLoading
        root.isVisible = !isLoading || !isError
        loading.isVisible = isLoading && !isError
    }

    private fun View.initMovieDetail(movie: Movie) {
        val image = findViewById<ImageView>(R.id.movieImage)
        val gradient = findViewById<ImageView>(R.id.gradient)
        val cast = findViewById<TextView>(R.id.cast)
        val movieTitle = findViewById<TextView>(R.id.movieTitle)
        val storyLineLabel = findViewById<TextView>(R.id.storyLineLabel)
        val reviewsNumber = findViewById<TextView>(R.id.reviewsNumber)
        val movieGenres = findViewById<TextView>(R.id.movieGenres)
        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        val minimalAge = findViewById<TextView>(R.id.minimalAge)

        val spanCount = resources.getInteger(R.integer.actorsSpanCount)

        findViewById<RecyclerView>(R.id.actorsRecyclerView).run {
            addItemDecoration(ItemDecorator(R.dimen.actor_offset, spanCount))
            adapter = ActorAdapter().apply { list = movie.actors }
            layoutManager = GridLayoutManager(context, spanCount, GridLayoutManager.VERTICAL, false)
        }

        Glide.with(context)
            .load(movie.backdrop)
            .into(image)

        Glide.with(context)
            .load(R.drawable.ic_gradient)
            .into(gradient)

        cast.isVisible = movie.actors.isNotEmpty()
        movieTitle.text = movie.title
        storyLineLabel.text = movie.overview
        reviewsNumber.text = context.getString(R.string.reviews_number, movie.numberOfRatings)
        movieGenres.text = movie.genres.split()
        ratingBar.rating = movie.ratings.calculateRating()
        minimalAge.text = getString(R.string.minimal_age, movie.minimumAge)
    }

    companion object {

        const val MOVIE_ID_ARG = "movie id arg"

        fun newInstance(movieId: Long) = MovieDetailsFragment().withArgs {
            putLong(MOVIE_ID_ARG, movieId)
        }
    }
}