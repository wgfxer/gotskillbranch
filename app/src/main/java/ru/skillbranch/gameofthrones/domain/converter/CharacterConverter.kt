package ru.skillbranch.gameofthrones.domain.converter

import ru.skillbranch.gameofthrones.data.remote.res.CharacterRes
import  ru.skillbranch.gameofthrones.data.local.entities.Character
import ru.skillbranch.gameofthrones.utils.OneWayConverter
import ru.skillbranch.gameofthrones.utils.StringUtils

/**
 * Конвертер персонажей data слоя в domain
 *
 * @author Valeriy Minnulin
 */
class CharacterConverter : OneWayConverter<CharacterRes, Character> {

    override fun convert(value: CharacterRes): Character {
        return Character(
            StringUtils.getIdFromUrl(value.url).toString(),
            value.name,
            value.gender,
            value.culture,
            value.born,
            value.died,
            value.titles,
            value.aliases,
            value.father,
            value.mother,
            value.spouse,
            StringUtils.EMPTY
        )
    }
}