package com.example.packme.retrofit

import com.example.packme.entity.Parking
import com.example.packme.entity.Utilisateur
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Endpoint {

    @GET("gestionParking/getAllParkings")
    suspend fun getAllParkings(): Response<List<Parking>>

    @POST("gestionParking/getParkingByName")
    suspend fun getParkingByName(@Body nom: String): Response<List<Parking>>

    @POST("getuser")
    suspend fun seConnecter(@Body utilisateur: Utilisateur): Response<String>

    @POST("utilisateur/sinscrire")
    suspend fun sinscrire(@Body utilisateur: Utilisateur): Response<String>

    companion object {
        @Volatile
        var endpoint: Endpoint? = null
        fun createInstance(): Endpoint {
            if(endpoint ==null) {
                synchronized(this) {
                    endpoint = Retrofit.Builder().baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create()).build()
                        .create(Endpoint::class.java)
                }
            }
            return endpoint!!

        }


    }
}