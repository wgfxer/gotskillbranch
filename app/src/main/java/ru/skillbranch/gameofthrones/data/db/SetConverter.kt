package ru.skillbranch.gameofthrones.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson

/**
 * Конвертер поля сущности базы данных типа Set
 *
 * @author Valeriy Minnulin
 */
class SetConverter {

    @TypeConverter
    fun fromString(value: String?): Set<String> {
        return Gson().fromJson(value, Array<String>::class.java).toSet()
    }

    @TypeConverter
    fun fromArraySet(set: Set<String>?): String {
        return Gson().toJson(set)
    }
}