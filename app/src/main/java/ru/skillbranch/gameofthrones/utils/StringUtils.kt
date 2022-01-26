package ru.skillbranch.gameofthrones.utils

/**
 * @author Valeriy Minnulin
 */
class StringUtils {

    companion object {

        fun getIdFromUrl(url: String): Int? {
            return url.substringAfterLast('/').toIntOrNull()
        }

        const val EMPTY = ""
        const val UNKNOWN = "Information is unknown"
    }
}