package be.bf.android.mymovies.lists

import android.content.Context
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.bf.android.mymovies.R
import be.bf.android.mymovies.entities.Movie
import com.bumptech.glide.Glide

class MovieListAdapter(private val context : Context,
                       private val movies : List<Movie>,
                       private val onClickListener : (movie : Movie) -> Unit) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        val tvTitle : TextView = view.findViewById(R.id.tv_mi_titre)
        val tvRating : TextView = view.findViewById(R.id.tv_mi_rating)
        val tvDate : TextView = view.findViewById(R.id.tv_mi_date)
        val imgMovie : ImageView = view.findViewById(R.id.img_mi_imgV)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val movie : Movie = movies[position]

        holder.tvTitle.text = movie.title
        holder.tvRating.text = movie.rating.toString()
        holder.tvDate.text = movie.date

        if (movie.imageV != null && movie.imageV != "https://image.tmdb.org/t/p/w500null") {
            Log.d("adapter", movie.imageV!!)
            Glide.with(context).load(movie.imageV).into(holder.imgMovie)
        }
        else if (movie.imageV == "https://image.tmdb.org/t/p/w500null") {
            holder.imgMovie.setImageResource(R.drawable.ic_launcher_background)
        }
        holder.itemView.setOnClickListener {
            onClickListener(movie)
        }

    }

    override fun getItemCount(): Int {
        return movies.size
    }

}