package ru.skillbranch.gameofthrones.models.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class Character(
    @PrimaryKey
    val id: String,
    val name: String,
    val gender: String,
    val culture: String,
    val born: String,
    val died: String,
    val titles: List<String> = listOf(),
    val aliases: List<String> = listOf(),
    val father: String,
    val mother: String,
    val spouse: String,
    val houses: Set<String>,
    val words: String
)

data class CharacterItem(
    val id: String,
    val house: String,
    val name: String,
    val titles: List<String>,
    val aliases: List<String>
)

data class CharacterFull(
    val id: String,
    val name: String,
    val words: String,
    val born: String,
    val died: String,
    val titles: List<String>,
    val aliases: List<String>,
    val house:String,
    val father: String?,
    val mother: String?
)

data class RelativeCharacter(
    val id: String,
    val name: String,
    val house:String
)