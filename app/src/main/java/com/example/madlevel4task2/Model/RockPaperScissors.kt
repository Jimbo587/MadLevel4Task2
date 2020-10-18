package com.example.madlevel4task2.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game")
data class RockPaperScissors(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var player: Int,
    var computer: Int,
    var winner: Int,
    var date: String
)