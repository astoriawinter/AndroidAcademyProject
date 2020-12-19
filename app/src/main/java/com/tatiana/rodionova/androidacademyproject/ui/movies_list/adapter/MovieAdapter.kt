package com.tatiana.rodionova.androidacademyproject.ui.movies_list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tatiana.rodionova.androidacademyproject.R
import com.tatiana.rodionova.androidacademyproject.custom.RatingBar
import com.tatiana.rodionova.androidacademyproject.model.Movie
import com.tatiana.rodionova.androidacademyproject.model.Movie.Companion.calculateRating
import com.tatiana.rodionova.androidacademyproject.model.Movie.Companion.split
import kotlin.properties.Delegates

class MovieAdapter(private val clickListener: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem == newItem

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem.id == newItem.id
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    var list: List<Movie> by Delegates.observable(listOf()) { _, _, list ->
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false),
            clickListener
        )

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    class MovieViewHolder(itemView: View, val clickListener: (Movie) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val context = itemView.context

        private val name = itemView.findViewById<TextView>(R.id.movieName)
        private val picture = itemView.findViewById<ImageView>(R.id.moviePicture)
        private val reviewsNumber = itemView.findViewById<TextView>(R.id.reviewsNumber)
        private val genres = itemView.findViewById<TextView>(R.id.movieGenres)
        private val ratingBar = itemView.findViewById<RatingBar>(R.id.ratingBar)
        private val movieLength = itemView.findViewById<TextView>(R.id.length)
        private val minimalAge = itemView.findViewById<TextView>(R.id.minimalAge)
        private val isFavourite = itemView.findViewById<ImageView>(R.id.isFavourite)

        fun bind(item: Movie) {
            itemView.setOnClickListener { clickListener(item) }

            Glide.with(itemView.context)
                .load(item.poster)
                .thumbnail(0.5f)
                .into(picture)

            minimalAge.text = context.getString(R.string.minimal_age, item.minimumAge)
            reviewsNumber.text = context.getString(R.string.reviews_number, item.numberOfRatings)
            genres.text = item.genres.split()
            ratingBar.rating = item.ratings.calculateRating()
            movieLength.text = context.getString(R.string.length, item.runtime)
            name.text = item.title
        }
    }
}
