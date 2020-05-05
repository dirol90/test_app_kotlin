package com.test.testapp1202.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.test.testapp1202.dao.FilmDao
import com.test.testapp1202.database.FilmDatabase
import com.test.testapp1202.database.FilmDatabaseProvider
import com.test.testapp1202.model.Film
import com.test.testapp1202.network.Api


class FilmRepository(application: Application) {
    private var filmDao: FilmDao
    private var allFilms: LiveData<List<Film>>

    init {
        val database: FilmDatabase =
            FilmDatabaseProvider.getInstance(application.applicationContext)
        filmDao = database.getFilmDao()
        allFilms = filmDao.getAll()
    }

    fun getAllFilms(): LiveData<List<Film>> {
        if (allFilms.value == null) {
            updateFilms()
        }
        return allFilms
    }

    fun getAllFilmsSortedASC(): LiveData<List<Film>> {
        allFilms = filmDao.getAllSortedASC()
        return allFilms
    }

    fun getAllFilmsSortedDESC(): LiveData<List<Film>> {
        allFilms = filmDao.getAllSortedDESC()
        return allFilms
    }


    fun updateFilms(): LiveData<List<Film>> {
        UpdateFilmsListAsync(filmDao).execute()
        return allFilms
    }

    private class UpdateFilmsListAsync(val filmDao: FilmDao) : AsyncTask<Unit, Unit, Unit>() {

        override fun doInBackground(vararg p0: Unit?) {
            val list = Api().getDataFromApi()
            filmDao.insertAll(list)
        }
    }

}