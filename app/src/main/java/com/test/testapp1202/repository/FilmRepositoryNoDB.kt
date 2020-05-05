package com.test.testapp1202.repository

import android.os.AsyncTask
import com.test.testapp1202.model.Film
import com.test.testapp1202.network.Api

class FilmRepositoryNoDB {
    private var allFilms: List<Film> = mutableListOf()

    init {
        allFilms = updateFilms()
    }

    fun getAllFilms(): List<Film> {
        if (allFilms.isEmpty()) {
            allFilms = updateFilms()
        }
        return allFilms
    }

    fun getAllFilmsSortedASC(): List<Film> {
        allFilms = allFilms.sortedWith(compareBy(Film::name))
        return allFilms
    }

    fun getAllFilmsSortedDESC(): List<Film> {
        allFilms = allFilms.sortedWith(compareBy(Film::name)).reversed()
        return allFilms
    }

    private fun updateFilms(): List<Film> {
        return UpdateFilmsListAsync().execute().get()
    }

    private class UpdateFilmsListAsync : AsyncTask<Unit, List<Film>, List<Film>>() {


        override fun doInBackground(vararg p0: Unit?): List<Film> {
            return Api().getDataFromApi()
        }

        override fun onPostExecute(result: List<Film>) {
            super.onPostExecute(result)
        }
    }
}