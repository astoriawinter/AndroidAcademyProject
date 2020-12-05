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
import kotlin.properties.Delegates

class MovieAdapter(private val clickListener: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem.poster == newItem.poster &&
                    oldItem.minimalAge == newItem.minimalAge &&
                    oldItem.backgroundPoster == newItem.backgroundPoster &&
                    oldItem.description == newItem.description &&
                    oldItem.isFavourite == newItem.isFavourite &&
                    oldItem.genres == newItem.genres &&
                    oldItem.rating == newItem.rating &&
                    oldItem.reviewNumber == newItem.reviewNumber &&
                    oldItem.length == newItem.length &&
                    oldItem.actors == newItem.actors

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem.name == newItem.name
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
                .into(picture)

            isFavourite.isActivated = item.isFavourite
            minimalAge.text = item.minimalAge
            reviewsNumber.text =
                itemView.context.getString(R.string.reviews_number, item.reviewNumber)
            genres.text = item.genres.joinToString(separator = ", ")
            ratingBar.rating = item.rating
            movieLength.text = itemView.context.getString(R.string.length, item.length)
            name.text = item.name
        }
    }
}
