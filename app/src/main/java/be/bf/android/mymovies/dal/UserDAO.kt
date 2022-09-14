package be.bf.android.mymovies.dal

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import be.bf.android.mymovies.entities.User
import java.io.Closeable

class UserDAO (private val context: Context): Closeable{

    companion object{

        const val CREATE_QUERY: String = "CREATE TABLE user(" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                " username VARCHAR(50) NOT NULL)"

        const val UPDATE_QUERY: String = "DROP TABLE user"
    }

    private val helper: DbHelper = DbHelper(context)

    private lateinit var database: SQLiteDatabase

    fun openWritable(): SQLiteDatabase?{
        return helper.writableDatabase.also { database = it }
    }

    fun openReadable(): SQLiteDatabase?{
        return helper.readableDatabase.also { database = it }
    }

    fun getUserFromCursor(cursor: Cursor): User?{

        val idColumn = cursor.getColumnIndex("id")
        val usernameColumn = cursor.getColumnIndex("username")

        if (idColumn >= 0 && usernameColumn >= 0) {

            val id = cursor.getInt(idColumn)
            val username = cursor.getString(usernameColumn)

            return User(id, username)
        }
        return null
    }

    fun findAll(): List<User?> {
        var users: MutableList<User?> = ArrayList()
        var cursor: Cursor = this.database.query("user", null, null, null, null, null, null)
        cursor.moveToFirst()

        do {
            val user = getUserFromCursor(cursor)
            users.add(user)
        }while (cursor.moveToNext())

        return users
    }

    fun insert(user: User): Long{
        var cv = ContentValues()
        cv.put("username", user.username)

        return database.insert("user", null, cv)
    }

    fun update(user: User): Int{

        var id: Int =user.id!!
        var cv = ContentValues()
        cv.put("username", user.username)

        return database.update("user", cv, "id= ?", arrayOf(id.toString()))
    }




    override fun close() {
        database.close()
    }

}