package be.bf.android.mymovies.entities

import java.util.*

data class Movie (

    var id: Int,
    var title: String,
    var rating: Float,
    var date: String,
    var image: String,
    var seen: Int,
    var userId: Int,
    var tagId: Int ){

}

