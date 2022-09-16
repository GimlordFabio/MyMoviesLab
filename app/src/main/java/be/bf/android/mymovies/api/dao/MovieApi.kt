package be.bf.android.mymovies.api.dao

import be.bf.android.mymovies.api.dto.ResponseMovie
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieApi {
    @GET("movie/upcoming")
    fun upcomingMovies(@Query("api_key") apiKey : String = "c9e23b610c2f0c1040a493fc10ce5aaf",
                       @Query("language") language : String = "en-US",
                       @Query("page") page : Int = 1,
                       @Query("region") region : String = "FR") : Call<ResponseMovie>
}