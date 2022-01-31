package ru.skillbranch.gameofthrones.domain

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.core.Single
import ru.skillbranch.gameofthrones.data.db.GameOfThronesDao
import ru.skillbranch.gameofthrones.data.local.entities.Character


/**
 * @author Valeriy Minnulin
 */
class CharactersInteractorImpl(
    private val gameOfThronesDao: GameOfThronesDao
): CharactersInteractor {

    override fun getCharacters() = gameOfThronesDao.getAllCharacters()

    override fun getCharacterLiveData(id: String): LiveData<Character> = gameOfThronesDao.getCharacterLiveData(id)

    override fun getCharacter(id: String) = gameOfThronesDao.getCharacter(id)

}