package ru.skillbranch.gameofthrones.presentation.viewmodel.characters

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.skillbranch.gameofthrones.domain.CharactersInteractor
import ru.skillbranch.gameofthrones.models.domain.HouseName
import ru.skillbranch.gameofthrones.utils.RxSchedulers
import ru.skillbranch.gameofthrones.data.local.entities.Character

/**
 * Вьюмодель для экрана со списком персонажей какого-то дома
 *
 * @author Valeriy Minnulin
 */
class CharactersViewModel(
    charactersInteractor: CharactersInteractor,
    rxSchedulers: RxSchedulers
): ViewModel() {

    private val allCharactersLiveData = MutableLiveData<List<Character>>()
    private val filteredCharactersLiveData = MutableLiveData<List<Character>>()

    init {
        charactersInteractor.getCharacters()
            .subscribeOn(rxSchedulers.getIoScheduler())
            .observeOn(rxSchedulers.getMainThreadScheduler())
            .subscribe(
                {
                    allCharactersLiveData.postValue(it)
                    filteredCharactersLiveData.postValue(it)
                },
                {
                    Log.e(TAG, "$it")
                    //do nothing
                }
            )
    }

    /**
     * Получить лайвдату со списком персонажей дома [house]
     */
    fun getCharactersForHouse(house: HouseName): LiveData<List<Character>> {
        return Transformations.map(filteredCharactersLiveData) { characters ->
            characters.filter { character -> character.houseId.contains(house.fullName) }
        }
    }

    /**
     * Установить фильтр [filter] для поиска персонажей по имени
     */
    fun setFilter(filter: String?) {
        val filteredCharacters = allCharactersLiveData.value?.filter { character ->
            filter?.let { character.name.contains(it, ignoreCase = true) } ?: true
        } ?: emptyList()
        filteredCharactersLiveData.postValue(filteredCharacters)
    }

    companion object {
        private const val TAG = "CharactersViewModel"
    }

}