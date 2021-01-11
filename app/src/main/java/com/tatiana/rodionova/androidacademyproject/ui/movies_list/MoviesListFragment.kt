package com.tatiana.rodionova.androidacademyproject.ui.movies_list

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tatiana.rodionova.androidacademyproject.R
import com.tatiana.rodionova.androidacademyproject.decorator.ItemDecorator
import com.tatiana.rodionova.androidacademyproject.model.Movie
import com.tatiana.rodionova.androidacademyproject.ui.movie_details.MovieDetailsFragment
import com.tatiana.rodionova.androidacademyproject.ui.movies_list.adapter.MovieAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {
    private val model: MovieListViewModel by viewModel()

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

        model.movies.observe(viewLifecycleOwner, { state ->
            when (state) {
                is MovieListState.Loading -> renderUIState(
                    view = view,
                    isLoading = true,
                    isError = false
                )
                is MovieListState.Error -> renderUIState(view = view, isLoading = true, isError = true)
                is MovieListState.Success -> {
                    renderUIState(view = view, isLoading = false, isError = false)
                    initAdapter(view, state.movie)
                }
            }
        })
    }

    private fun renderUIState(view: View, isLoading: Boolean, isError: Boolean) = with(view) {
        val root = findViewById<NestedScrollView>(R.id.root)
        val loading = findViewById<ProgressBar>(R.id.loading)
        val error = findViewById<TextView>(R.id.error)

        error.isVisible = isError && !isLoading
        root.isVisible = !isLoading || !isError
        loading.isVisible = isLoading && !isError
    }

    private fun initAdapter(view: View, movies: List<Movie>) = with(view) {
        val spanCount = resources.getInteger(R.integer.spanCount)

        findViewById<RecyclerView>(R.id.moviesRecycler).run {
            addItemDecoration(ItemDecorator(R.dimen.movie_offset, spanCount))
            layoutManager = GridLayoutManager(context, spanCount, GridLayoutManager.VERTICAL, false)
            adapter = MovieAdapter(::selectMovie).apply { list = movies }
        }
    }

    private fun selectMovie(movie: Movie) {
        parentFragmentManager.run {
            beginTransaction()
                .add(R.id.container, MovieDetailsFragment.newInstance(movie.id))
                .addToBackStack(MovieDetailsFragment::class.java.simpleName)
                .commit()
        }
    }

    companion object {

        fun newInstance() = MoviesListFragment()
    }
}