package be.bf.android.mymovies.details

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import be.bf.android.mymovies.R
import be.bf.android.mymovies.dal.MovieDAO
import be.bf.android.mymovies.dal.UserDAO
import be.bf.android.mymovies.databinding.ActivityDetailsBinding
import be.bf.android.mymovies.databinding.ActivityMainBinding
import be.bf.android.mymovies.entities.Movie
import be.bf.android.mymovies.lists.MainActivity
import be.bf.android.mymovies.login.LoginActivity
import com.bumptech.glide.Glide


/**
 * Activity that manages each movie details
 */
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    private lateinit var movieDAO: MovieDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieDAO = MovieDAO(this)
        val movie = intent.getSerializableExtra("movie") as Movie

        if (movie.seen == null) {

            val preferences: SharedPreferences = this.getSharedPreferences("userSharedPref", Context.MODE_PRIVATE)
            val id = preferences.getInt("id", 0)

            val currentMovie = movieDAO.findOneMovieByUser(id, movie.id)

            Log.d("DetailsActivity", currentMovie.toString())

            if (currentMovie != null) {
                movie.seen = currentMovie.seen
                Log.d("DetailActivity", movie.seen.toString())
            }
        }

        when(movie.seen) {

            0 -> {
                binding.tvDaAddWatch.visibility = View.INVISIBLE
                binding.ivDaLeftDel.visibility = View.VISIBLE
            }
            1 -> {
                binding.tvDaAddSeen.visibility = View.INVISIBLE
                binding.ivDaRightDel.visibility = View.VISIBLE
                binding.ivDaSeenIcon.visibility = View.VISIBLE
                binding.ivDaNotSeenIcon.visibility = View.INVISIBLE
            }
        }


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

        binding.iconDvBack.setOnClickListener {
            finish()
        }

        binding.tvDaScreenings.setOnClickListener{

            var url = titleToUrl(movie.title)

            startActivity(Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(url) })

        }


        binding.tvDaAddWatch.setOnClickListener{

            val preferences: SharedPreferences = this.getSharedPreferences("userSharedPref", Context.MODE_PRIVATE)
            val id = preferences.getInt("id", 0)
            movie.seen = 0
            movie.userId = id // SharedPreferences

            movieDAO.insert(movie)
            movieDAO.close()

            binding.tvDaAddWatch.visibility = View.INVISIBLE
            binding.ivDaLeftDel.visibility = View.VISIBLE

            binding.tvDaAddSeen.visibility = View.VISIBLE
            binding.ivDaRightDel.visibility = View.INVISIBLE

        }
        binding.tvDaAddSeen.setOnClickListener{

            val preferences: SharedPreferences = this.getSharedPreferences("userSharedPref", Context.MODE_PRIVATE)
            val id = preferences.getInt("id", 0)

            movie.seen = 1
            movie.userId = id // SharedPreferences


            movieDAO.insert(movie)
            movieDAO.close()

            binding.tvDaAddSeen.visibility = View.INVISIBLE
            binding.ivDaRightDel.visibility = View.VISIBLE

            binding.tvDaAddWatch.visibility = View.VISIBLE
            binding.ivDaLeftDel.visibility = View.INVISIBLE
        }

        binding.ivDaLeftDel.setOnClickListener {
            movieDAO.delete(movie.id)

            binding.tvDaAddWatch.visibility = View.VISIBLE
            binding.ivDaLeftDel.visibility = View.INVISIBLE
        }

        binding.ivDaRightDel.setOnClickListener {
            movieDAO.delete(movie.id)

            binding.tvDaAddSeen.visibility = View.VISIBLE
            binding.ivDaRightDel.visibility = View.INVISIBLE
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_app_bar_home,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val preferences: SharedPreferences = getSharedPreferences("userSharedPref", Context.MODE_PRIVATE)

        when (item.itemId) {
            R.id.logout -> {

                preferences.edit().clear().apply()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)

                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }

    }

    fun titleToUrl(title: String): String{

        val title = title.lowercase().replace(" ", "-")
        val url = "https://www.cinenews.be/fr/films/" + title

        return url
    }



}