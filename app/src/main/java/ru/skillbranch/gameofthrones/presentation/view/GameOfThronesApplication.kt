package ru.skillbranch.gameofthrones.presentation.view

import android.app.Application
import androidx.room.Room
import ru.skillbranch.gameofthrones.data.db.AppDatabase

/**
 * @author Valeriy Minnulin
 */
class GameOfThronesApplication: Application() {

    val db: AppDatabase by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "got-database").build()
    }

}