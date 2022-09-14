package be.bf.android.mymovies.dal

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import be.bf.android.mymovies.entities.Note
import java.io.Closeable

class NoteDAO (private val context: Context): Closeable{

    companion object {

        const val CREATE_QUERY: String = "CREATE TABLE note(" +
                " id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                " noteContent TEXT, " +
                " movieId INTEGER, " +
                " userId INTEGER," +
                " CONSTRAINT fk_movie FOREIGN KEY (movieId) REFERENCES movie(id),"+
                " CONSTRAINT fk_user FOREIGN KEY (userId) REFERENCES user(id))"

        const val UPDATE_QUERY: String = "DROP TABLE note"


    }

    private val helper: DbHelper = DbHelper(context)

    private lateinit var database: SQLiteDatabase

    fun openWritable(): SQLiteDatabase?{
        return helper.writableDatabase.also { database = it }
    }

    fun openReadable(): SQLiteDatabase?{
        return helper.readableDatabase.also { database = it }
    }

    fun getNoteFromCursor(cursor: Cursor): Note?{

        val idColumn = cursor.getColumnIndex("id")
        val noteContentColumn = cursor.getColumnIndex("noteContent")
        val movieIdColumn = cursor.getColumnIndex("movieId")
        val userIdColumn = cursor.getColumnIndex("userId")

        if( idColumn >=0 && noteContentColumn >=0 && movieIdColumn >=0 && userIdColumn >=0){

            val id = cursor.getInt(idColumn)
            val noteContent = cursor.getString(noteContentColumn)
            val movieId = cursor.getInt(movieIdColumn)
            val userId = cursor.getInt(userIdColumn)

            return Note(id, noteContent, movieId, userId)
        }
        return null
    }

    fun findAll(): List<Note?>{

        var notes: MutableList<Note?> = ArrayList()
        var cursor: Cursor = this.database.query("note", null, null, null, null, null, null,)
        cursor.moveToFirst()

        do {
            val note = getNoteFromCursor(cursor)
            notes.add(note)

        }while (cursor.moveToNext())

        return notes
    }

    fun insert (note: Note): Long{

        var cv = ContentValues()
        cv.put("noteContent", note.noteContent)
        cv.put("movieId", note.movieId)
        cv.put("userId", note.userId)

        return database.insert("noteContent", null, cv)
    }

    fun update (note: Note): Int{

        var id: Int = note.id!!
        var cv = ContentValues()

        cv.put("noteContent", note.noteContent)
        cv.put("movieId", note.movieId)
        cv.put("userId", note.userId)

        return database.update("note", cv, "id= ?", arrayOf(id.toString()))

    }


    override fun close() {
        database.close()
    }
}