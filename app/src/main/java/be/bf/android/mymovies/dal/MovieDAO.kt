package be.bf.android.mymovies.dal

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import be.bf.android.mymovies.entities.Movie
import java.io.Closeable

class MovieDAO (private val context: Context): Closeable {

    companion object{

        const val CREATE_QUERY: String = "CREATE TABLE movie(" +
                "id INTEGER NOT NULL PRIMARY KEY," +
                " title TEXT," +
                " rating DOUBLE," +
                " date TEXT," +
                " imageV TEXT," +
                " imageH TEXT," +
                " overview TEXT," +
                " seen INTEGER," +
                " userId INTEGER," +
                " CONSTRAINT fk_user FOREIGN KEY (userId) REFERENCES user(id))"

        const val UPDATE_QUERY: String = "DROP TABLE movie"
    }

    private val helper: DbHelper = DbHelper(context)

    private lateinit var database: SQLiteDatabase

    fun openWritable(): SQLiteDatabase?{
        return helper.writableDatabase.also { database = it }
    }

    fun openReadable(): SQLiteDatabase?{
        return helper.readableDatabase.also { database = it }
    }

    fun getMovieFromCursor(cursor: Cursor): Movie?{

        val idColumn = cursor.getColumnIndex("id")
        val titleColumn = cursor.getColumnIndex("title")
        val ratingColumn = cursor.getColumnIndex("rating")
        val dateColumn = cursor.getColumnIndex("date")
        val imageVColumn = cursor.getColumnIndex("imageV")
        val imageHColumn = cursor.getColumnIndex("imageH")
        val overviewColumn = cursor.getColumnIndex("overview")
        val seenColumn = cursor.getColumnIndex("seen")
        val userIDColumn = cursor.getColumnIndex("userId")

        if (idColumn >= 0 && titleColumn >= 0 && ratingColumn >= 0 && dateColumn >= 0 && imageVColumn >= 0 && imageHColumn >= 0 && overviewColumn >= 0 && seenColumn >= 0 && userIDColumn >= 0){

            val id = cursor.getInt(idColumn)
            val title = cursor.getString(titleColumn)
            val rating = cursor.getDouble(ratingColumn)
            val date = cursor.getString(dateColumn)
            val imageV = cursor.getString(imageVColumn)
            val imageH = cursor.getString(imageHColumn)
            val overview = cursor.getString(overviewColumn)
            val seen = cursor.getInt(seenColumn)
            val userId = cursor.getInt(userIDColumn)

            return  Movie(id, title, rating, date, imageV, imageH, overview, seen, userId)
        }
        return null
    }

    fun findAllWatchList(): List<Movie>{

        openReadable()

        var movies: MutableList<Movie> = ArrayList()
        // SELECT * FROM movie WHERE seen = 0
        var cursor: Cursor = this.database.rawQuery("SELECT * FROM movie WHERE seen = 0", null)
        val isNotEmpty = cursor.moveToFirst()

        if (!isNotEmpty) return movies

        do {
            val movie = getMovieFromCursor(cursor)
            if (movie != null) {
                movies.add(movie)
            }

        }while (cursor.moveToNext())

        return movies
    }

    fun findAllSeen(): List<Movie>{

        openReadable()

        var movies: MutableList<Movie> = ArrayList()
        // SELECT * FROM movie WHERE seen = 1
        var cursor: Cursor = this.database.query("movie", null, "seen = ?", arrayOf("1"), null, null, null)
        val isNotEmpty = cursor.moveToFirst()
        if (!isNotEmpty) return movies
        do {
            val movie = getMovieFromCursor(cursor)
            if (movie != null) {
                movies.add(movie)
            }

        }while (cursor.moveToNext())

        return movies
    }

    fun insert (movie: Movie): Long{

        openWritable()

        var cv = ContentValues()
        cv.put("id", movie.id)
        cv.put("title", movie.title)
        cv.put("rating", movie.rating)
        cv.put("date", movie.date)
        cv.put("imageV", movie.imageV)
        cv.put("imageH", movie.imageH)
        cv.put("overview", movie.overview)
        cv.put("seen", movie.seen)
        cv.put("userId", movie.userId)


        // Rappel : la methode insert retourne toujours l id de l item creer

        return database.insert("movie", null, cv)
    }

    fun update (movie: Movie): Int{

        openWritable()

        var id: Int = movie.id!!
        var cv = ContentValues()

        cv.put("title", movie.title)
        cv.put("rating", movie.rating)
        cv.put("date", movie.date)
        cv.put("imageV", movie.imageV)
        cv.put("imageH", movie.imageH)
        cv.put("overview", movie.overview)
        cv.put("seen", movie.seen)
        cv.put("userId", movie.userId)


        return database.update("movie", cv, "id= ?", arrayOf(id.toString()))
    }


    override fun close() {
        database.close()
    }

}