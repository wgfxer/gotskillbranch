package ru.skillbranch.gameofthrones.models.domain

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import ru.skillbranch.gameofthrones.R

/**
 * @author Valeriy Minnulin
 */
enum class HouseName(
    val shortName: String,
    val fullName: String,
    @ColorRes val colorRes: Int,
    @DrawableRes val iconRes: Int,
    @DrawableRes val coastOfArmsRes: Int,
    @ColorRes val accentColorRes: Int
) {
    STARK(
        "Stark",
        "House Stark of Winterfell",
        R.color.stark_primary,
        R.drawable.stark_icon,
        R.drawable.stark_coast_of_arms,
        R.color.stark_accent
    ),
    LANNISTER(
        "Lannister",
        "House Lannister of Casterly Rock",
        R.color.lannister_primary,
        R.drawable.lannister_icon,
        R.drawable.lannister_coast_of_arms,
        R.color.lannister_accent
    ),
    TARGARYEN(
        "Targaryen",
        "House Targaryen of King's Landing",
        R.color.targaryen_primary,
        R.drawable.targaryen_icon,
        R.drawable.targaryen_coast_of_arms,
        R.color.targaryen_accent
    ),
    BARATHEON(
        "Baratheon",
        "House Baratheon of Dragonstone",
        R.color.baratheon_primary,
        R.drawable.baratheon_icon,
        R.drawable.baratheon_coast_of_arms,
        R.color.baratheon_accent
    ),
    GREYJOY(
        "Greyjoy",
        "House Greyjoy of Pyke",
        R.color.greyjoy_primary,
        R.drawable.greyjoy_icon,
        R.drawable.greyjoy_coast_of_arms,
        R.color.greyjoy_accent
    ),
    MARTEL(
        "Martel",
        "House Nymeros Martell of Sunspear",
        R.color.martel_primary,
        R.drawable.martel_icon,
        R.drawable.martel_coast_of_arms,
        R.color.martel_accent
    ),
    TYRELL(
        "Tyrell",
        "House Tyrell of Highgarden",
        R.color.tyrell_primary,
        R.drawable.tyrell_icon,
        R.drawable.tyrell_coast_of_arms,
        R.color.tyrell_accent
    ),
}

val HOUSES_ORDERED = listOf(
    HouseName.STARK,
    HouseName.LANNISTER,
    HouseName.TARGARYEN,
    HouseName.BARATHEON,
    HouseName.GREYJOY,
    HouseName.MARTEL,
    HouseName.TYRELL
)