package be.bf.android.mymovies.dal

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import be.bf.android.mymovies.entities.Movie_Tag
import java.io.Closeable

class Movie_TagDAO (private val context: Context): Closeable {

    companion object{

        const val CREATE_QUERY: String = "CREATE TABLE movie_tag(movieId INTEGER NOT NULL, tagId INTEGER NOT NULL, CONSTRAINT PK_movie_tag PRIMARY KEY(movieId, tagId))"
        const val UPDATE_QUERY: String = "DROP TABLE IF EXISTS movie_tag"

    }

    private val  helper: DbHelper = DbHelper(context)

    private lateinit var database: SQLiteDatabase

    fun openWritable(): SQLiteDatabase?{
        return helper.writableDatabase.also { database = it }
    }

    fun openReadable(): SQLiteDatabase?{
        return helper.readableDatabase.also { database = it }
    }

    fun insert(movie_tag: Movie_Tag): Long{
        var cv = ContentValues()
        cv.put("movieId", movie_tag.movieId)
        cv.put("tagId", movie_tag.tagId)

        return database.insert("movieId", null, cv)
    }



    override fun close() {
        database.close()
    }
}