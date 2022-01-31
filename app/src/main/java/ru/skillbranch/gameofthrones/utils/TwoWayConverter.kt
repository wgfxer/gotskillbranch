package ru.skillbranch.gameofthrones.utils

/**
 * @author Valeriy Minnulin
 */
interface TwoWayConverter<T, E> {
    fun convert(value: T): E

    fun revert(value: E): T
}