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
                addItemDecoration(ItemDecorator(resources.getDimension(R.dimen.actor_offset).toInt(), 2))
                adapter = MovieAdapter(::selectMovie).apply { list = getMovieData() }
                layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
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
                            minimalAge = 13,
                            resource = R.drawable.poster_with_mask,
                            isFavourite = false,
                            genres = listOf("Action", "Adventure", "Drama"),
                            rating = 4.0f,
                            reviewNumber = 125,
                            length = 137
                    )
            )

    companion object {

        fun newInstance() = MoviesListFragment()
    }
}