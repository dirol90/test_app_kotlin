package com.test.testapp1202.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.test.testapp1202.model.Film
import com.test.testapp1202.repository.FilmRepository
import com.test.testapp1202.repository.FilmRepositoryNoDB


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: FilmRepository = FilmRepository(application)
    private var repositoryNoDB: FilmRepositoryNoDB = FilmRepositoryNoDB()
    private var allFilm: LiveData<List<Film>> = repository.getAllFilms()
    private var allFilmWithoutDB: List<Film> = repositoryNoDB.getAllFilms()

    private var isUseDatabase: Boolean = false
    private var selectedItem: Int = 0
    private var lastSortOrder: String = "SORT A-Z"


    fun setLastSortOrder(lastSortOrder: String) {
        this.lastSortOrder = lastSortOrder
    }

    fun getLastSortOrder(): String? {
        return lastSortOrder
    }

    fun setSelectedItem(selectedItem: Int) {
        this.selectedItem = selectedItem
    }

    fun getSelecteditem(): Int {
        return selectedItem
    }

    fun getIsUseDatabase(): Boolean {
        return isUseDatabase
    }

    fun setIsUseDatabase(b: Boolean) {
        isUseDatabase = b
    }


    fun getFilms(): LiveData<List<Film>> {
        allFilm = if (lastSortOrder == "SORT Z-A") {
            repository.getAllFilmsSortedASC()
        } else {
            repository.getAllFilmsSortedDESC()
        }

        return allFilm
    }


    fun getFilmsWithoutDB(): List<Film> {
        allFilmWithoutDB = if (lastSortOrder == "SORT Z-A") {
            repositoryNoDB.getAllFilmsSortedASC()
        } else {
            repositoryNoDB.getAllFilmsSortedDESC()
        }

        return allFilmWithoutDB
    }

}