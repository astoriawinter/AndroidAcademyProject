package com.tatiana.rodionova.androidacademyproject.ui.movies_list

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tatiana.rodionova.androidacademyproject.R
import com.tatiana.rodionova.androidacademyproject.decorator.ItemDecorator
import com.tatiana.rodionova.androidacademyproject.model.Actor
import com.tatiana.rodionova.androidacademyproject.model.Movie
import com.tatiana.rodionova.androidacademyproject.ui.movie_details.MovieDetailsFragment
import com.tatiana.rodionova.androidacademyproject.ui.movies_list.adapter.MovieAdapter

class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {

    override fun onAttach(context: Context) {
        super.onAttach(context)

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finishAndRemoveTask()
                }
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.run {
            findViewById<RecyclerView>(R.id.moviesRecycler).run {
                addItemDecoration(ItemDecorator(R.dimen.movie_offset, spanCount))
                adapter = MovieAdapter(::selectMovie).apply { list = getMovieData() }
                layoutManager = GridLayoutManager(context, spanCount, GridLayoutManager.VERTICAL, false)
            }
        }
    }

    private fun selectMovie(movie: Movie) {
        fragmentManager?.run {
            beginTransaction()
                .add(R.id.container, MovieDetailsFragment.newInstance(movie))
                .addToBackStack(MovieDetailsFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun getMovieData(): List<Movie> =
        listOf(
            Movie(
                name = "Avengers: End Game",
                minimalAge = "13+",
                poster = R.drawable.avengers_with_mask,
                backgroundPoster = R.drawable.avengers_logo,
                isFavourite = false,
                description = getString(R.string.avengers_description),
                genres = listOf("Action", "Adventure", "Drama"),
                rating = 4.0f,
                reviewNumber = 125,
                length = 137,
                actors = listOf(
                    Actor("Robert Downey Jr.", R.drawable.robert_downey),
                    Actor("Chris Evans", R.drawable.chris_evans),
                    Actor("Mark Ruffalo", R.drawable.mark_ruffalo),
                    Actor("Chris Hemsworth", R.drawable.chris_hemsworth)
                )
            ),
            Movie(
                name = "Tenet",
                minimalAge = "16+",
                poster = R.drawable.tenet_with_mask,
                isFavourite = true,
                description = getString(R.string.tenet_description),
                backgroundPoster = R.drawable.tenet,
                genres = listOf("Action", "Sci-Fi", "Thriller"),
                rating = 5.0f,
                reviewNumber = 98,
                length = 97,
                actors = listOf(
                    Actor("Robert Downey Jr.", R.drawable.robert_downey),
                    Actor("Chris Evans", R.drawable.chris_evans),
                    Actor("Mark Ruffalo", R.drawable.mark_ruffalo),
                    Actor("Chris Hemsworth", R.drawable.chris_hemsworth)
                )
            ),
            Movie(
                name = "Black Widow",
                minimalAge = "13+",
                poster = R.drawable.black_widow_with_mask,
                isFavourite = false,
                description = getString(R.string.black_widow_description),
                backgroundPoster = R.drawable.black_widow,
                genres = listOf("Action", "Adventure", "Sci-Fi"),
                rating = 4.0f,
                reviewNumber = 38,
                length = 102,
                actors = listOf(
                    Actor("Robert Downey Jr.", R.drawable.robert_downey),
                    Actor("Chris Evans", R.drawable.chris_evans),
                    Actor("Mark Ruffalo", R.drawable.mark_ruffalo),
                    Actor("Chris Hemsworth", R.drawable.chris_hemsworth)
                )
            ),
            Movie(
                name = "Wonder Woman 1984",
                minimalAge = "13+",
                poster = R.drawable.ww84_with_mask,
                backgroundPoster = R.drawable.ww84,
                isFavourite = false,
                description = getString(R.string.ww84_description),
                genres = listOf("Action", "Adventure", "Fantasy"),
                rating = 5.0f,
                reviewNumber = 74,
                length = 120,
                actors = listOf(
                    Actor("Robert Downey Jr.", R.drawable.robert_downey),
                    Actor("Chris Evans", R.drawable.chris_evans),
                    Actor("Mark Ruffalo", R.drawable.mark_ruffalo),
                    Actor("Chris Hemsworth", R.drawable.chris_hemsworth)
                )
            )
        )

    companion object {

        private const val spanCount = 2

        fun newInstance() = MoviesListFragment()
    }
}