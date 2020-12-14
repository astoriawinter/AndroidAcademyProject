package com.tatiana.rodionova.androidacademyproject.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
        val name: String,
        val minimalAge: String,
        val poster: Int,
        val backgroundPoster: Int,
        val description: String? = null,
        val isFavourite: Boolean,
        val genres: List<String>,
        val rating: Float,
        val reviewNumber: Int,
        val length: Int,
        val actors: List<Actor>
): Parcelable
