package ru.skillbranch.gameofthrones.domain

import io.reactivex.rxjava3.core.Completable

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