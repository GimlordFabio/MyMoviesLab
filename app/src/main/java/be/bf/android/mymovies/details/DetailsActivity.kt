package be.bf.android.mymovies.details

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import be.bf.android.mymovies.R
import be.bf.android.mymovies.dal.MovieDAO
import be.bf.android.mymovies.databinding.ActivityDetailsBinding
import be.bf.android.mymovies.databinding.ActivityMainBinding
import be.bf.android.mymovies.entities.Movie
import com.bumptech.glide.Glide

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movie = intent.getSerializableExtra("movie") as Movie

        binding.tvDaTitle.text = movie.title
        binding.tvDaDate.text = movie.date
        binding.tvDaRating.text = movie.rating.toString()

        if (movie.imageH != null && movie.imageH != "https://image.tmdb.org/t/p/w500null"){
            Glide.with(this).load(movie.imageH).into(binding.imgDaImageH)
        }
        else if (movie.imageH == "https://image.tmdb.org/t/p/w500null"){
            binding.imgDaImageH.setImageResource(R.drawable.ic_launcher_background)
        }
        binding.tvDaOverview.text = movie.overview



    }
}