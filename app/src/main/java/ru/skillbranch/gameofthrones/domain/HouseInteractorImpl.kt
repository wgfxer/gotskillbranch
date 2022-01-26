package ru.skillbranch.gameofthrones.domain

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.skillbranch.gameofthrones.data.db.CharacterDao
import ru.skillbranch.gameofthrones.data.remote.GameOfThronesApi
import ru.skillbranch.gameofthrones.domain.converter.CharacterConverter
import ru.skillbranch.gameofthrones.models.data.HouseRes
import ru.skillbranch.gameofthrones.models.domain.Character
import ru.skillbranch.gameofthrones.models.domain.HOUSES_ORDERED
import ru.skillbranch.gameofthrones.utils.StringUtils
import java.util.concurrent.TimeUnit

/**
 * @author Valeriy Minnulin
 */
class HouseInteractorImpl(
    private val gameOfThronesApi: GameOfThronesApi,
    private val characterConverter: CharacterConverter,
    private val characterDao: CharacterDao
): HouseInteractor {

    override fun initializeDatabase(): Completable {
        val isDatabaseEmpty = characterDao.getAllCharacters().map { it.isEmpty() }

        return isDatabaseEmpty.flatMapCompletable { isEmpty ->
            if (isEmpty) {
                makeRequestAndSaveInDb()
            } else {
                Completable.complete().delay(DB_REQUEST_DELAY_SECONDS, TimeUnit.SECONDS)
            }
        }
    }

    private fun makeRequestAndSaveInDb(): Completable {
        val housesSingle = Single.concat(HOUSES_ORDERED.map { getHouseByNameSingle(it.fullName) }).toList()

        val charactersSingle = housesSingle
            .flatMap { houses ->
                getCharactersSingle(houses)
            }

        return charactersSingle
            .doOnSuccess { characterDao.insertAll(getCharactersWithoutRepetition(it)) }
            .ignoreElement()
    }

    private fun getCharactersWithoutRepetition(characters: List<Character>): List<Character> {
        return characters.groupBy { it.id }.map { entry ->
            if (entry.value.size > 1) {
                entry.value.first().copy(houses = entry.value.map { it.houses }.reduceOrNull { acc, set ->
                    acc + set
                }.orEmpty())
            } else {
                entry.value.first()
            }
        }
    }


    private fun getCharactersSingle(houses: List<HouseRes>): Single<List<Character>> {
        val charactersSingles = houses
            .map { house ->
                val characterIds = house.swornMembers.mapNotNull { url -> StringUtils.getIdFromUrl(url) }
                characterIds.map { id -> getCharacterSingle(id, house) }
            }.flatten()
        return Single.concat(charactersSingles).map {
            it
        }.toList().map {
            it
        }
    }


    private fun getCharacterSingle(id: Int, houseRes: HouseRes): Single<Character> {
        return gameOfThronesApi.getCharacter(id)
            .map { characterConverter.convert(it) }
            .map { it.copy(houses = setOf(houseRes.name), words = houseRes.words) }
    }

    private fun getHouseByNameSingle(houseName: String): Single<HouseRes> {
        return gameOfThronesApi.getHouses(houseName).map {
            val house = it.first()
            house
        }
    }

    companion object {
        private const val DB_REQUEST_DELAY_SECONDS = 0L
        private const val TAG = "HouseInteractorImpl"
    }
}