package com.tatiana.rodionova.androidacademyproject.ui.movie_details.adapter

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
import com.tatiana.rodionova.androidacademyproject.model.Actor
import com.tatiana.rodionova.androidacademyproject.utils.setGradient
import kotlin.properties.Delegates

class ActorAdapter : RecyclerView.Adapter<ActorAdapter.ActorViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Actor>() {

        override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean =
            oldItem.picture == newItem.picture

        override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean =
            oldItem.name == newItem.name
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    var list: List<Actor> by Delegates.observable(listOf()) { _, _, list ->
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder =
        ActorViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_actor, parent, false)
        )

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    class ActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name = itemView.findViewById<TextView>(R.id.name)
        private val picture = itemView.findViewById<ImageView>(R.id.picture)

        fun bind(item: Actor) {
            Glide.with(itemView.context)
                .load(item.picture)
                .fallback(R.drawable.ic_baseline_no_photography_24)
                .into(picture)

            with(name) {
                text = item.name
                setGradient()
            }
        }
    }
}
