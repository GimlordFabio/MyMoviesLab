package be.bf.android.mymovies.entities

import org.w3c.dom.Text

data class Note (
    var id: Int,
    var noteContent: String,
    var movieId: Int,
    var userId: Int){
}