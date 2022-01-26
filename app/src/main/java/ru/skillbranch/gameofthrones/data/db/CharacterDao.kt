package ru.skillbranch.gameofthrones.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Single
import ru.skillbranch.gameofthrones.models.domain.Character

/**
 * Data Access Object для таблицы с персонажами
 *
 * @author Valeriy Minnulin
 */
@Dao
interface CharacterDao {

    /**
     * Добавление всех персонажей в бд
     */
    @Insert
    fun insertAll(characters: List<Character>)

    /**
     * Получение всех персонажей из бд
     */
    @Query("SELECT * FROM characters")
    fun getAllCharacters(): Single<List<Character>>

    /**
     * Получение персонажа из бд по айди
     */
    @Query("SELECT * FROM characters WHERE id = :id")
    fun getCharacterLiveData(id: String): LiveData<Character>

    /**
     * Получение персонажа из бд по айди
     */
    @Query("SELECT * FROM characters WHERE id = :id")
    fun getCharacter(id: String): Character?
}