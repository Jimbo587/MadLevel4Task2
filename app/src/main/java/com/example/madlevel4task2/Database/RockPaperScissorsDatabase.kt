package com.example.madlevel4task2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.madlevel4task2.dao.RockPaperScissorsDao
import com.example.madlevel4task2.model.RockPaperScissors

@Database(entities = [RockPaperScissors::class], version = 2, exportSchema = false)
abstract class RockPaperScissorsDatabase : RoomDatabase() {

    abstract fun rockPaperScissorsDao(): RockPaperScissorsDao

    companion object {
        private const val DATABASE_NAME = "ROCK_PAPER_SCISSORS_DATABASE"

        @Volatile
        private var rockPaperScissorsRoomDatabaseInstance: RockPaperScissorsDatabase? = null

        fun getDatabase(context: Context): RockPaperScissorsDatabase? {
            if (rockPaperScissorsRoomDatabaseInstance == null) {
                synchronized(RockPaperScissorsDatabase::class.java) {
                    if (rockPaperScissorsRoomDatabaseInstance == null) {
                        rockPaperScissorsRoomDatabaseInstance =
                            Room.databaseBuilder(
                                context.applicationContext,
                                RockPaperScissorsDatabase::class.java, DATABASE_NAME
                            )
                                .fallbackToDestructiveMigration().build()
                    }
                }
            }
            return rockPaperScissorsRoomDatabaseInstance
        }
    }

}
