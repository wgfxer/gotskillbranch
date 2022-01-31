package ru.skillbranch.gameofthrones.domain

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.core.Single
import ru.skillbranch.gameofthrones.data.local.entities.Character


/**
 * @author Valeriy Minnulin
 */
interface CharactersInteractor {

    /**
     * Возвращает single со списком персонажей дома [house]
     */
    fun getCharacters(): Single<List<Character>>

    /**
     * Возвращает лайвдату с персонажем по айди [id]
     */
    fun getCharacterLiveData(id: String): LiveData<Character>

    /**
     * Возвращает модель с персонажем по айди [id]
     */
    fun getCharacter(id: String): Character?


}