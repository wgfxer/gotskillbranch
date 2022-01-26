package ru.skillbranch.gameofthrones.utils

/**
 * @author Valeriy Minnulin
 */
interface OneWayConverter<T, E> {

    fun convert(value: T): E
}