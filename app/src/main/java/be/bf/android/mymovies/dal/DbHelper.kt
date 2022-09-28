package be.bf.android.mymovies.dal

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DbHelper (context: Context): SQLiteOpenHelper (context, DB_NAME, null, DB_VERSION){

    companion object{
        const val DB_NAME: String = "Database"
        const val DB_VERSION: Int = 14
    }

    override fun onCreate(p0: SQLiteDatabase?) {

//        if (p0 != null){
//            p0.execSQL(UserDAO.CREATE_QUERY)
        Log.d("DbHelper", "Pass Create")
        p0?.execSQL(UserDAO.CREATE_QUERY)
        p0?.execSQL(IconeDAO.CREATE_QUERY)
        p0?.execSQL(TagDAO.CREATE_QUERY)
        p0?.execSQL(MovieDAO.CREATE_QUERY)
        p0?.execSQL(NoteDAO.CREATE_QUERY)
        p0?.execSQL(Movie_TagDAO.CREATE_QUERY)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

        Log.d("DbHelper", "Pass Upgrade")
        p0?.execSQL(UserDAO.UPDATE_QUERY)
        p0?.execSQL(IconeDAO.UPDATE_QUERY)
        p0?.execSQL(TagDAO.UPDATE_QUERY)
        p0?.execSQL(MovieDAO.UPDATE_QUERY)
        p0?.execSQL(NoteDAO.UPDATE_QUERY)
        p0?.execSQL(Movie_TagDAO.UPDATE_QUERY)
    }
}