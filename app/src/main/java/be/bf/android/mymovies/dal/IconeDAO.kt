package be.bf.android.mymovies.dal

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.drawable.Icon
import be.bf.android.mymovies.entities.Icone
import java.io.Closeable

class IconeDAO (private val context: Context): Closeable{

    companion object{

        const val CREATE_QUERY: String = "CREATE TABLE icone(" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                " src TEXT)"

        const val UPDATE_QUERY: String = "DROP TABLE icone"
    }

    private val helper: DbHelper = DbHelper(context)

    private lateinit var database: SQLiteDatabase

    fun openWritable(): SQLiteDatabase?{
        return helper.writableDatabase.also { database = it }
    }

    fun openReadable(): SQLiteDatabase?{
        return helper.readableDatabase.also { database = it }
    }

    fun getIconeFromCursor(cursor: Cursor): Icone?{

        val idColumn = cursor.getColumnIndex("id")
        val srcColumn = cursor.getColumnIndex("src")

        if (idColumn >= 0 && srcColumn <= 0){

            val id = cursor.getInt(idColumn)
            val src = cursor.getString(srcColumn)

            return Icone(id, src)
        }
        return null
    }

    fun findAll(): List<Icone?>{

        var icones: MutableList<Icone?> = ArrayList()
        var cursor: Cursor = this.database.query("icone", null, null, null, null, null, null,)
        cursor.moveToFirst()

        do {
            val icone = getIconeFromCursor(cursor)
            icones.add(icone)
        }while (cursor.moveToNext())

        return icones
    }

    fun insert (icone: Icone): Long{

        var cv = ContentValues()
        cv.put("src", icone.src)

        return database.insert("src", null, cv)
    }

    fun update (icone: Icone): Int{

        var id: Int = icone.id!!
        var cv = ContentValues()

        cv.put("src", icone.src)

        return database.update("icone", cv, "id=?", arrayOf(id.toString()))
    }


    override fun close() {
        database.close()
    }

}