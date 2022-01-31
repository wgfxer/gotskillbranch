package ru.skillbranch.gameofthrones.domain.converter

import ru.skillbranch.gameofthrones.data.remote.res.HouseRes
import ru.skillbranch.gameofthrones.data.local.entities.House
import ru.skillbranch.gameofthrones.models.domain.FULLNAMES_MAP
import ru.skillbranch.gameofthrones.models.domain.SHORTNAMES_MAP
import ru.skillbranch.gameofthrones.utils.OneWayConverter
import ru.skillbranch.gameofthrones.utils.TwoWayConverter

/**
 * @author Valeriy Minnulin
 */
class HouseConverter: TwoWayConverter<HouseRes, House> {

    override fun convert(value: HouseRes): House {
        val shortName = SHORTNAMES_MAP[value.name] ?: ""
        return House(
            shortName,
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

    override fun revert(value: House): HouseRes {
        val fullName = FULLNAMES_MAP[value.name] ?: ""
        return HouseRes(
            fullName,
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