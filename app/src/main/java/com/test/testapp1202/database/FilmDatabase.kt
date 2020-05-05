package com.test.testapp1202.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.test.testapp1202.dao.FilmDao
import com.test.testapp1202.model.Film

@Database(entities = [Film::class], version = 1)
abstract class FilmDatabase : RoomDatabase() {
    abstract fun getFilmDao(): FilmDao
}

object FilmDatabaseProvider {
    private const val dbName: String = "film-list.db"
    private var instance: FilmDatabase? = null

    fun getInstance(context: Context): FilmDatabase {
        if (instance == null) {
            instance =
                Room.databaseBuilder(context.applicationContext, FilmDatabase::class.java, dbName)
                    .build()
        }

        return instance!!
    }
}

