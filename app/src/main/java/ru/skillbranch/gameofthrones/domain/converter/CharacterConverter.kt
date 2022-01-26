package ru.skillbranch.gameofthrones.domain.converter

import ru.skillbranch.gameofthrones.models.data.CharacterRes
import ru.skillbranch.gameofthrones.models.domain.Character
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
            value.url,
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
            emptySet(),
            StringUtils.EMPTY
        )
    }
}