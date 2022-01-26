package ru.skillbranch.gameofthrones.domain

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.core.Single
import ru.skillbranch.gameofthrones.data.db.CharacterDao
import ru.skillbranch.gameofthrones.models.domain.Character

/**
 * @author Valeriy Minnulin
 */
class CharactersInteractorImpl(
    private val characterDao: CharacterDao
): CharactersInteractor {

    override fun getCharacters(): Single<List<Character>> {
        return characterDao.getAllCharacters()
    }

    override fun getCharacterLiveData(id: String): LiveData<Character> = characterDao.getCharacterLiveData(id)
    override fun getCharacter(id: String) =
        characterDao.getCharacter(id)

}