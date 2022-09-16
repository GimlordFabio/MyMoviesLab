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
import retrofit2.create

class MainViewModel(): ViewModel() {

    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }

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
            getInTheatresMovie()
        }
    }


    fun getUpcomingMovie() {

        val api = RetrofitClient.client.create(MovieApi::class.java)

        api.upcomingMovies().enqueue(object : Callback<ResponseMovie> {

            override fun onResponse(call: Call<ResponseMovie>, response: Response<ResponseMovie>) {

                response.body()?.let {
                     Log.d("VM", "Call")
                    _moviesUpcoming.clear()
                    _moviesUpcoming.addAll(responseToMovie(it))
                    moviesUpcoming.value = _moviesUpcoming
                }

            }

            override fun onFailure(call: Call<ResponseMovie>, t: Throwable) {
                 Log.d("VM", "Fail call")
            }

        })
    }

    fun getInTheatresMovie(){

        val api = RetrofitClient.client.create(MovieApi::class.java)

        api.inTheatresMovies().enqueue(object : Callback<ResponseMovie> {

            override fun onResponse(call: Call<ResponseMovie>, response: Response<ResponseMovie>) {

                response.body()?.let {
                     Log.d("VM", "Call")
                    _moviesInTheatre.clear()
                    _moviesInTheatre.addAll(responseToMovie((it)))
                    moviesInTheatre.value = _moviesInTheatre
                }
            }

            override fun onFailure(call: Call<ResponseMovie>, t: Throwable) {
                 Log.d("VM", "Fail call")
            }

        })
    }

    fun getWatchlist(){
        // TODO: a faire
    }


    fun getSeenMovie() {
        // TODO: a faire
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
                imageV = IMAGE_URL + item.poster_path,
                imageH = IMAGE_URL + item.backdrop_path,
                overview = item.overview

            )
            listeMovie.add(movie)

        }

        return listeMovie;

    }


}