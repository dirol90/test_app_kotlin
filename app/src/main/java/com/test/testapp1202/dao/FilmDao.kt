package com.test.testapp1202.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.test.testapp1202.model.Film

@Dao
interface FilmDao {
    @Query("SELECT * FROM film_items")
    fun getAll(): LiveData<List<Film>>

    @Query("SELECT * FROM film_items ORDER BY name ASC")
    fun getAllSortedASC(): LiveData<List<Film>>

    @Query("SELECT * FROM film_items ORDER BY name DESC")
    fun getAllSortedDESC(): LiveData<List<Film>>

    @Query("SELECT * FROM film_items WHERE itemId LIKE :itemId")
    fun findById(itemId: String): Film

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(filmList: List<Film>)

    @Delete
    fun delete(film: Film)

    @Update
    fun updateFilm(film: Film)

}