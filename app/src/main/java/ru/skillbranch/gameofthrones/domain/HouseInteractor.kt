package ru.skillbranch.gameofthrones.domain

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.skillbranch.gameofthrones.models.domain.Character
import ru.skillbranch.gameofthrones.models.domain.House

/**
 * @author Valeriy Minnulin
 */
interface HouseInteractor {

    /**
     * Возвращает completable о начальной инициализации(либо запрос и запись в бд, либо если в базе уже есть данные то
     * дилей 5 секунд)
     */
    fun initializeDatabase(): Completable
}