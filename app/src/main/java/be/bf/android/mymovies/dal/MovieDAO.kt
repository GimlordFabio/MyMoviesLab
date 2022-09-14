package be.bf.android.mymovies.dal

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import be.bf.android.mymovies.entities.Movie
import java.io.Closeable

class MovieDAO (private val context: Context): Closeable {

    companion object{

        const val CREATE_QUERY: String = "CREATE TABLE movie(" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                " title VARCHAR(50)," +
                " rating FLOAT," +
                " date VARCHAR(15)," +
                " image VARCHAR(100)," +
                " seen INTEGER," +
                " userId INTEGER," +
                " tagId INTEGER" +
                " CONSTRAINT fk_user FOREIGN KEY (userId) REFERENCES user(id)" +
                " CONSTRAINT fk_tag FOREIGN KEY (tagId) REFERENCES tag(id))"

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
        val imageColumn = cursor.getColumnIndex("image")
        val seenColumn = cursor.getColumnIndex("seen")
        val userIDColumn = cursor.getColumnIndex("userId")
        val tagIDColumn = cursor.getColumnIndex("tagId")

        if (idColumn >= 0 && titleColumn >= 0 && ratingColumn >= 0 && dateColumn >= 0 && imageColumn>= 0 && seenColumn>= 0 && userIDColumn >= 0 && tagIDColumn >= 0){

            val id = cursor.getInt(idColumn)
            val title = cursor.getString(titleColumn)
            val rating = cursor.getFloat(ratingColumn)
            val date = cursor.getString(dateColumn)
            val image = cursor.getString(imageColumn)
            val seen = cursor.getInt(seenColumn)
            val userId = cursor.getInt(userIDColumn)
            val tagId = cursor.getInt(tagIDColumn)

            return  Movie(id, title, rating, date, image, seen, userId, tagId)
        }
        return null
    }

    fun findAll(): List<Movie?>{

        var movies: MutableList<Movie?> = ArrayList()
        var cursor: Cursor = this.database.query("movie", null, null, null, null, null, null)
        cursor.moveToFirst()

        do {
            val movie = getMovieFromCursor(cursor)
            movies.add(movie)

        }while (cursor.moveToNext())

        return movies
    }

    fun insert (movie: Movie): Long{

        var cv = ContentValues()
        cv.put("title", movie.title)
        cv.put("rating", movie.rating)
        cv.put("date", movie.date)
        cv.put("image", movie.image)
        cv.put("seen", movie.seen)
        cv.put("userId", movie.userId)
        cv.put("tagId", movie.tagId)

        // Rappel : la methode insert retourne toujours l id de l item creer

        return database.insert("title", null, cv)
    }

    fun upgrade (movie: Movie): Int{

        var id: Int = movie.id!!
        var cv = ContentValues()

        cv.put("title", movie.title)
        cv.put("rating", movie.rating)
        cv.put("date", movie.date)
        cv.put("image", movie.image)
        cv.put("seen", movie.seen)
        cv.put("userId", movie.userId)
        cv.put("tagId", movie.tagId)

        return database.update("movie", cv, "id= ?", arrayOf(id.toString()))
    }


    override fun close() {
        database.close()
    }

}