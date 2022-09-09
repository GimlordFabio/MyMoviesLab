package be.bf.android.mymovies.entities

import java.util.*

data class Movie (

    var id: Integer,
    var title: String,
    var rating: Float,
    var date: String,
    var image: String,
    var seen: Integer,
    var userId: Integer,
    var tagId: Integer ){

}

