package ru.skillbranch.gameofthrones.utils

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * @author Valeriy Minnulin
 */
class RxSchedulersImpl: RxSchedulers {
    override fun getIoScheduler(): Scheduler {
        return Schedulers.io()
    }

    override fun getMainThreadScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}