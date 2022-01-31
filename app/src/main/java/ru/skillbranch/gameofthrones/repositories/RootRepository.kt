package ru.skillbranch.gameofthrones.repositories

import androidx.annotation.VisibleForTesting
import ru.skillbranch.gameofthrones.data.local.entities.CharacterFull
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem
import ru.skillbranch.gameofthrones.data.remote.res.CharacterRes
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes
import ru.skillbranch.gameofthrones.presentation.view.GameOfThronesApplication
import ru.skillbranch.gameofthrones.data.local.entities.Character
import ru.skillbranch.gameofthrones.data.local.entities.RelativeCharacter
import ru.skillbranch.gameofthrones.data.remote.GameOfThronesService
import ru.skillbranch.gameofthrones.domain.converter.CharacterConverter
import ru.skillbranch.gameofthrones.domain.converter.HouseConverter
import ru.skillbranch.gameofthrones.models.domain.HOUSES_ORDERED
import ru.skillbranch.gameofthrones.utils.StringUtils

object RootRepository {

    val db = GameOfThronesApplication.DB!!

    val api = GameOfThronesService.create()

    /**
     * Получение данных о всех домах из сети
     * @param result - колбек содержащий в себе список данных о домах
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getAllHouses(result : (houses : List<HouseRes>) -> Unit) {
        val houses = HOUSES_ORDERED.map { api.getHouses(it.fullName).blockingGet() }.flatten()
        result(houses)
    }

    /**
     * Получение данных о требуемых домах по их полным именам из сети
     * @param houseNames - массив полных названий домов (смотри AppConfig)
     * @param result - колбек содержащий в себе список данных о домах
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getNeedHouses(vararg houseNames: String, result : (houses : List<HouseRes>) -> Unit) {
        val houses = houseNames.map { api.getHouses(it).blockingGet() }.flatten()
        result(houses)
    }

    /**
     * Получение данных о требуемых домах по их полным именам и персонажах в каждом из домов из сети
     * @param houseNames - массив полных названий домов (смотри AppConfig)
     * @param result - колбек содержащий в себе список данных о доме и персонажей в нем (Дом - Список Персонажей в нем)
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getNeedHouseWithCharacters(vararg houseNames: String, result : (houses : List<Pair<HouseRes, List<CharacterRes>>>) -> Unit) {
        val d = houseNames.map { api.getHouses(it).blockingGet() }.flatten()
        result(d.map { it to it.swornMembers.map { api.getCharacter(StringUtils.getIdFromUrl(it)!!).blockingGet() } })
    }

    /**
     * Запись данных о домах в DB
     * @param houses - Список персонажей (модель HouseRes - модель ответа из сети)
     * необходимо произвести трансформацию данных
     * @param complete - колбек о завершении вставки записей db
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun insertHouses(houses : List<HouseRes>, complete: () -> Unit) {
        val housesConverter = HouseConverter()
        db.getGameOfThronesDao().insertHouses(houses.map { housesConverter.convert(it) })
        complete()
    }

    /**
     * Запись данных о пересонажах в DB
     * @param Characters - Список персонажей (модель CharacterRes - модель ответа из сети)
     * необходимо произвести трансформацию данных
     * @param complete - колбек о завершении вставки записей db
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun insertCharacters(characters : List<CharacterRes>, complete: () -> Unit) {
        val charactersConverter = CharacterConverter()
        db.getGameOfThronesDao().insertCharacters(characters.map { charactersConverter.convert(it) })
        complete()
    }

    /**
     * При вызове данного метода необходимо выполнить удаление всех записей в db
     * @param complete - колбек о завершении очистки db
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun dropDb(complete: () -> Unit) {
        db.clearAllTables()
        complete()
    }

    /**
     * Поиск всех персонажей по имени дома, должен вернуть список краткой информации о персонажах
     * дома - смотри модель CharacterItem
     * @param name - краткое имя дома (его первычный ключ)
     * @param result - колбек содержащий в себе список краткой информации о персонажах дома
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun findCharactersByHouseName(name : String, result: (characters : List<CharacterItem>) -> Unit) {
        val characters = db.getGameOfThronesDao().getCharactersForHouse(name)
        val characterItems = characters.map {
            CharacterItem(
                id = it.id,
                house = it.houseId,
                name = it.name,
                titles = it.titles,
                aliases = it.aliases,
            )
        }
        result(characterItems)
    }

    /**
     * Поиск персонажа по его идентификатору, должен вернуть полную информацию о персонаже
     * и его родственных отношения - смотри модель CharacterFull
     * @param id - идентификатор персонажа
     * @param result - колбек содержащий в себе полную информацию о персонаже
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun findCharacterFullById(id : String, result: (character : CharacterFull) -> Unit) {
        val character: Character = db.getGameOfThronesDao().getCharacter(id)!!
        val motherCharacter: Character = db.getGameOfThronesDao().getCharacter(character.mother)!!
        val fatherCharacter: Character = db.getGameOfThronesDao().getCharacter(character.father)!!
        val mother = RelativeCharacter(
            id = motherCharacter.id,
            name = motherCharacter.name,
            house = motherCharacter.houseId,
        )
        val father = RelativeCharacter(
            id = fatherCharacter.id,
            name = fatherCharacter.name,
            house = fatherCharacter.houseId,
        )
        val houses = db.getGameOfThronesDao().getAllHouses().blockingGet()
        val house = houses.first { it.name == character.houseId }
        val characterFull = CharacterFull(
            id = character.id,
            name = character.name,
            words = house.words,
            born =  character.born,
            died = character.died,
            titles = character.titles,
            aliases = character.aliases,
            house = character.houseId,
            father = father,
            mother = mother
        )
        result(characterFull)
    }

    /**
     * Метод возвращет true если в базе нет ни одной записи, иначе false
     * @param result - колбек о завершении очистки db
     */
    fun isNeedUpdate(result: (isNeed : Boolean) -> Unit){
        val characters = db.getGameOfThronesDao().getAllCharacters().blockingGet()
        val houses = db.getGameOfThronesDao().getAllHouses().blockingGet()
        result(characters.isEmpty() && houses.isEmpty())
    }

}