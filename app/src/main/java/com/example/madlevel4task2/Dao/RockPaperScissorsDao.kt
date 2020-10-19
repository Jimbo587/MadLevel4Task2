package com.example.madlevel4task2.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.madlevel4task2.model.RockPaperScissors

@Dao
interface RockPaperScissorsDao {

    @Query("SELECT * FROM game")
    suspend fun getAllGames(): List<RockPaperScissors>

    @Query("SELECT COUNT(*) FROM game WHERE winner = 0")
    suspend fun getAllPlayerWins(): Int

    @Query("SELECT COUNT(*) FROM game WHERE winner = 1")
    suspend fun getAllDraws(): Int

    @Query("SELECT COUNT(*) FROM game WHERE winner = 2")
    suspend fun getAllCpuWins(): Int

    @Insert
    suspend fun insertGame(game: RockPaperScissors)

    @Query("DELETE FROM game")
    suspend fun deleteHistory()

}