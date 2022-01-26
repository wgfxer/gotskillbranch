package ru.skillbranch.gameofthrones.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * Конвертер поля типа List<String> сущности базы данных
 *
 * @author Valeriy Minnulin
 */
class ListConverter {
    @TypeConverter
    fun fromString(value: String?): List<String> {
        return Gson().fromJson(value, Array<String>::class.java).toList()
    }

    @TypeConverter
    fun fromArrayList(list: List<String>?): String {
        return Gson().toJson(list)
    }
}