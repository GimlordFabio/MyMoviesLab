package be.bf.android.mymovies.entities

import org.w3c.dom.Text

data class Notes (
    var id: Integer,
    var note: String,
    var moviesId: Integer,
    var userId: Integer){
}