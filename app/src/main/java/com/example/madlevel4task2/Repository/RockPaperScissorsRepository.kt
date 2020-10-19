package com.example.madlevel4task2.repository

import android.content.Context
import com.example.madlevel4task2.dao.RockPaperScissorsDao
import com.example.madlevel4task2.database.RockPaperScissorsDatabase
import com.example.madlevel4task2.model.RockPaperScissors

class RockPaperScissorsRepository(context: Context) {
    private val rockPaperScissorsDao: RockPaperScissorsDao

    init {
        val database = RockPaperScissorsDatabase.getDatabase(context)
        rockPaperScissorsDao = database!!.rockPaperScissorsDao()
    }

    suspend fun getAllGames(): List<RockPaperScissors> =
        rockPaperScissorsDao.getAllGames()

    suspend fun getAllPlayerWins(): Int =
        rockPaperScissorsDao.getAllPlayerWins()

    suspend fun getAllDraws(): Int =
        rockPaperScissorsDao.getAllDraws()

    suspend fun getAllCpuWins(): Int =
        rockPaperScissorsDao.getAllCpuWins()

    suspend fun insertGame(game: RockPaperScissors) =
        rockPaperScissorsDao.insertGame(game)

    suspend fun deleteHistory() =
        rockPaperScissorsDao.deleteHistory()
}