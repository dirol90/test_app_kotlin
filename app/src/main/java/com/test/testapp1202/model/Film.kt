package com.test.testapp1202.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "film_items")
data class Film(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "itemId") val itemId: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "time") val time: Long
)