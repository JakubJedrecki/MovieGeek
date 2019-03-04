package com.jedrula.moviegeek.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Genre(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String
)