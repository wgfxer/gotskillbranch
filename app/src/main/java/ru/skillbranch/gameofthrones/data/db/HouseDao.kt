package ru.skillbranch.gameofthrones.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Single
import ru.skillbranch.gameofthrones.models.domain.House

/**
 * Data Access Object для таблицы с домами
 *
 * @author Valeriy Minnulin
 */
@Dao
interface HouseDao {

    /**
     * Добавление всех домов в бд
     */
    @Insert
    fun insertAll(houses: List<House>)

    /**
     * Получение всех домов из бд
     */
    @Query("SELECT * FROM houses")
    fun getAllHouses(): Single<List<House>>
}