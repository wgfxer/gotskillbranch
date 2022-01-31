package ru.skillbranch.gameofthrones.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.skillbranch.gameofthrones.data.local.entities.House
import ru.skillbranch.gameofthrones.data.local.entities.Character

/**
 * @author Valeriy Minnulin
 */
@Database(entities = [House::class, Character::class], version = 1)
@TypeConverters(ListConverter::class, SetConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getGameOfThronesDao(): GameOfThronesDao

}