package ru.skillbranch.gameofthrones.domain.converter

import ru.skillbranch.gameofthrones.models.data.HouseRes
import ru.skillbranch.gameofthrones.models.domain.House
import ru.skillbranch.gameofthrones.utils.OneWayConverter

/**
 * @author Valeriy Minnulin
 */
class HouseConverter: OneWayConverter<HouseRes, House> {

    override fun convert(value: HouseRes): House {
        return House(
            value.url,
            value.name,
            value.region,
            value.coatOfArms,
            value.words,
            value.titles,
            value.seats,
            value.currentLord,
            value.heir,
            value.overlord,
            value.founded,
            value.founder,
            value.diedOut,
            value.ancestralWeapons
        )
    }
}