package be.bf.android.mymovies.dal

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import be.bf.android.mymovies.entities.User
import java.io.Closeable
import java.security.KeyStore

class UserDAO (private val context: Context): Closeable{

    companion object{

        const val CREATE_QUERY: String = "CREATE TABLE user(" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                " username TEXT NOT NULL UNIQUE)"

        const val UPDATE_QUERY: String = "DROP TABLE IF EXISTS user"
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

        val id = cursor.getInt(idColumn)
        val username = cursor.getString(usernameColumn)

        return User(id, username)
    }

    fun findUserByUsername(username: String): User? {
        Log.d("UserDAO", username)

        val db = database
        val query = "select * from user where username = ?"
        val cursor = db.rawQuery(query, arrayOf(username))
        val users = mutableListOf<User?>()
        while (cursor.moveToNext()) {
            val user = getUserFromCursor(cursor)
            users.add(user)
        }
        Log.d("UserDAO", users.toString())
        cursor.close()
        return if (users.size > 0) return users[0] else return null
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