package ru.skillbranch.gameofthrones.presentation.viewmodel.start

import android.util.Log
import androidx.lifecycle.ViewModel
import ru.skillbranch.gameofthrones.domain.HouseInteractor
import ru.skillbranch.gameofthrones.presentation.viewmodel.characters.CharactersViewModel
import ru.skillbranch.gameofthrones.utils.RxSchedulers
import ru.skillbranch.gameofthrones.utils.SingleLiveEvent

/**
 * Вьюмодель для экрана начальной инициализации
 *
 * @author Valeriy Minnulin
 */
class StartViewModel(
    houseInteractor: HouseInteractor,
    rxSchedulers: RxSchedulers,
): ViewModel() {

    private val _loadingErrorEvent = SingleLiveEvent<Unit>()
    val loadingErrorEvent = _loadingErrorEvent

    private val _goToNextScreenEvent = SingleLiveEvent<Unit>()
    val goToNextScreenEvent = _goToNextScreenEvent

    init {
        houseInteractor.initializeDatabase()
            .subscribeOn(rxSchedulers.getIoScheduler())
            .observeOn(rxSchedulers.getMainThreadScheduler())
            .subscribe(
                { _goToNextScreenEvent.call() },
                {
                    Log.e(TAG, "$it")
                    _loadingErrorEvent.call()
                }
            )
    }

    companion object {
        private const val TAG = "StartViewModel"
    }

}