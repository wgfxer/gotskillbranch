package ru.skillbranch.gameofthrones.data.remote

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.skillbranch.gameofthrones.models.data.CharacterRes
import ru.skillbranch.gameofthrones.models.data.HouseRes

/**
 * @author Valeriy Minnulin
 */
interface GameOfThronesApi {

    @GET("houses")
    fun getHouses(@Query("name") houseName: String): Single<List<HouseRes>>

    @GET("characters/{id}")
    fun getCharacter(@Path("id") characterId: Int): Single<CharacterRes>
}