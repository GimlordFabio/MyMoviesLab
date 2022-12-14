package be.bf.android.mymovies.entities

import java.io.Serializable
import java.util.*

data class Movie (

    var id: Int,
    var title: String,
    var rating: Double,
    var date: String,
    var imageV: String?,
    var imageH: String?,
    var overview: String,
    var seen: Int? = null,
    var userId: Int? = null) : Serializable {

}

