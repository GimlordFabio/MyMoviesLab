package be.bf.android.mymovies.lists

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.bf.android.mymovies.R
import be.bf.android.mymovies.entities.Movie

class MovieListAdapter(private val movies : List<Movie>) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val tvTitle : TextView = view.findViewById(R.id.tv_mi_titre)
        val tvRating : TextView = view.findViewById(R.id.tv_mi_rating)
        val tvDate : TextView = view.findViewById(R.id.tv_mi_date)
        val tvImage : ImageView = view.findViewById(R.id.img_mi_imgV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return movies.size
    }

}