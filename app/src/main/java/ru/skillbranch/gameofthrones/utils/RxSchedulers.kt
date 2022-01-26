package ru.skillbranch.gameofthrones.utils

import io.reactivex.rxjava3.core.Scheduler

/**
 * @author Valeriy Minnulin
 */
interface RxSchedulers {

    fun getIoScheduler(): Scheduler

    fun getMainThreadScheduler(): Scheduler
}