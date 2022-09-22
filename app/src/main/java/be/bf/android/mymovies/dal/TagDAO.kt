package be.bf.android.mymovies.dal

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import be.bf.android.mymovies.entities.Tag
import java.io.Closeable

class TagDAO (private val context: Context): Closeable {


    companion object{

        val CREATE_QUERY: String = "CREATE TABLE tag(" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                " tagName TEXT," +
                " tagColor TEXT," +
                " iconId INTEGER," +
                "CONSTRAINT fk_icon FOREIGN KEY (iconId) REFERENCES icon(id))"

        val UPDATE_QUERY: String = "DROP TABLE tag"
    }

    private val helper: DbHelper = DbHelper(context)

    private lateinit var database: SQLiteDatabase

    fun openWritable(): SQLiteDatabase?{
        return helper.writableDatabase.also { database = it }
    }

    fun openReadable(): SQLiteDatabase?{
        return helper.readableDatabase.also { database = it }
    }

    fun getTagFromCursor (cursor: Cursor): Tag?{

        val idColumn = cursor.getColumnIndex("id")
        val tagNameColumn = cursor.getColumnIndex("tagName")
        val tagColorColumn = cursor.getColumnIndex("tagColor")
        val iconIdColumn = cursor.getColumnIndex("iconId")

        if(idColumn >= 0 && tagNameColumn >= 0 && tagColorColumn >= 0 && iconIdColumn >= 0){

            val id = cursor.getInt(idColumn)
            val tagName = cursor.getString(tagNameColumn)
            val tagColor = cursor.getString(tagColorColumn)
            val iconId = cursor.getInt(iconIdColumn)

            return Tag(id, tagName, tagColor, iconId)
        }
        return null
    }

    fun findAll(): List<Tag?>{

        var tags: MutableList<Tag?> = ArrayList()
        var cursor: Cursor = this.database.query("tag", null, null, null, null, null, null,)
        cursor.moveToFirst()

        do {
            val tag = getTagFromCursor(cursor)
            tags.add(tag)

        }while (cursor.moveToNext())

        return tags
    }

    fun insert (tag: Tag): Long{

        var cv = ContentValues()
        cv.put("tagName", tag.tagName)
        cv.put("tagColor", tag.tagColor)
        cv.put("iconId", tag.iconId)

        return database.insert("tagName", null, cv)
    }

    fun update (tag: Tag): Int{

        var id: Int = tag.id!!
        var cv = ContentValues()

        cv.put("tagName", tag.tagName)
        cv.put("tagColor", tag.tagColor)
        cv.put("iconId", tag.iconId)

        return database.update("tag", cv, "id= ?", arrayOf(id.toString()))
    }




    override fun close() {
        database.close()
    }
}