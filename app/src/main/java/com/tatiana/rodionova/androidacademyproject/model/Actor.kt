package com.tatiana.rodionova.androidacademyproject.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Actor(
    val name: String,
    val resource: Int
): Parcelable
