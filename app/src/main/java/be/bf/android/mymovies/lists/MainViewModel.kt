package be.bf.android.mymovies.lists

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.bf.android.mymovies.api.RetrofitClient
import be.bf.android.mymovies.api.dao.MovieApi
import be.bf.android.mymovies.api.dto.ResponseMovie
import be.bf.android.mymovies.dal.MovieDAO
import be.bf.android.mymovies.entities.Movie
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(): ViewModel() {

    private val _moviesUpcoming = mutableListOf<Movie>()
    private val moviesUpcoming : MutableLiveData<List<Movie>> = MutableLiveData()
    val MoviesUpcoming : LiveData<List<Movie>>
        get() = moviesUpcoming

    private val _moviesInTheatre = mutableListOf<Movie>()
    private val moviesInTheatre : MutableLiveData<List<Movie>> = MutableLiveData()
    val MoviesInTheatre : LiveData<List<Movie>>
        get() = moviesInTheatre

    private val _moviesWatchList = mutableListOf<Movie>()
    private val moviesWatchList : MutableLiveData<List<Movie>> = MutableLiveData()
    val MoviesWatchList : LiveData<List<Movie>>
        get() = moviesWatchList

    private val _moviesSeen = mutableListOf<Movie>()
    private val moviesSeen : MutableLiveData<List<Movie>> = MutableLiveData()
    val MoviesSeen : LiveData<List<Movie>>
        get() = moviesSeen

    private val counter : MutableLiveData<Int> = MutableLiveData(0)
    val Counter : LiveData<Int>
        get() = counter

    init {
        viewModelScope.launch {
            getUpcomingMovie()
        }
    }


    fun getUpcomingMovie() {

        val api = RetrofitClient.client.create(MovieApi::class.java)

        api.upcomingMovies().enqueue(object : Callback<ResponseMovie> {

            override fun onResponse(call: Call<ResponseMovie>, response: Response<ResponseMovie>) {

                response.body()?.let {
                    _moviesUpcoming.clear();
                    _moviesUpcoming.addAll(responseToMovie(it))
                    moviesUpcoming.value = _moviesUpcoming
                }

            }

            override fun onFailure(call: Call<ResponseMovie>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getSeenMovie() {

    }

    // Translate the response from api to a Movie from my app

    fun responseToMovie(response : ResponseMovie) : ArrayList<Movie> {

        var listeMovie: ArrayList<Movie> = ArrayList<Movie>()

        for (item in response.results) {

            var movie = Movie(

                id = item.id,
                title = item.title,
                rating = item.vote_average,
                date = item.release_date,
                imageV = item.poster_path,
                imageH = item.backdrop_path,
                overview = item.overview

            )
            listeMovie.add(movie)

        }

        return listeMovie;

    }


}