package ru.skillbranch.gameofthrones.presentation.viewmodel.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Single
import ru.skillbranch.gameofthrones.domain.CharactersInteractor
import ru.skillbranch.gameofthrones.utils.RxSchedulers
import ru.skillbranch.gameofthrones.data.local.entities.Character


/**
 * @author Valeriy Minnulin
 */
class CharacterInfoViewModel(
    private val charactersInteractor: CharactersInteractor,
    private val rxSchedulers: RxSchedulers
): ViewModel() {

    private val _parentsLiveData = MutableLiveData<Pair<Character?, Character?>>()
    val parentsLiveData: LiveData<Pair<Character?, Character?>> = _parentsLiveData

    /**
     * Получить лайвдату с персонажем по айди [id]
     */
    fun getCharacterLiveData(id: String): LiveData<Character> = charactersInteractor.getCharacterLiveData(id)

    fun loadParents(motherUrl: String, fatherUrl: String) {
        val single = Single.fromCallable {
            val motherCharacter =  charactersInteractor.getCharacter(motherUrl)
            val fatherCharacter =  charactersInteractor.getCharacter(fatherUrl)
            motherCharacter to fatherCharacter
        }
        single
            .subscribeOn(rxSchedulers.getIoScheduler())
            .observeOn(rxSchedulers.getMainThreadScheduler())
            .subscribe({
                _parentsLiveData.value = it
            },{
                //do nothing
            }  )
    }
}