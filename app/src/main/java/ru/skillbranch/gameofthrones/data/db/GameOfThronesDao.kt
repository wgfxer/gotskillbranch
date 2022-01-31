package ru.skillbranch.gameofthrones.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Single
import ru.skillbranch.gameofthrones.data.local.entities.Character
import ru.skillbranch.gameofthrones.data.local.entities.House

/**
 * Data Access Object для таблицы с домами
 *
 * @author Valeriy Minnulin
 */
@Dao
interface GameOfThronesDao {

    //region Houses

    /**
     * Добавление всех домов в бд
     */
    @Insert
    fun insertHouses(houses: List<House>)

    /**
     * Получение всех домов из бд
     */
    @Query("SELECT * FROM houses")
    fun getAllHouses(): Single<List<House>>

    /**
     * Удалить все из таблицы с домами
     */
    @Query("DELETE FROM houses")
    fun dropHousesTable()


    //region Characters

    /**
     * Добавление всех персонажей в бд
     */
    @Insert
    fun insertCharacters(characters: List<Character>)

    /**
     * Получение всех персонажей из бд
     */
    @Query("SELECT * FROM characters")
    fun getAllCharacters(): Single<List<Character>>

    /**
     * Получение персонажа из бд по id
     */
    @Query("SELECT * FROM characters WHERE id = :id")
    fun getCharacterLiveData(id: String): LiveData<Character>

    /**
     * Получение персонажа из бд по id
     */
    @Query("SELECT * FROM characters WHERE id = :id")
    fun getCharacter(id: String): Character?

    /**
     * Удалить все из таблицы с персонажами
     */
    @Query("DELETE FROM characters")
    fun dropCharactersTable()

    @Query("SELECT * FROM characters where houseId = :house")
    fun getCharactersForHouse(house: String): List<Character>
}