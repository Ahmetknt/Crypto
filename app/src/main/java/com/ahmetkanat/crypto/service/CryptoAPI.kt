package com.ahmetkanat.crypto.service

import com.ahmetkanat.crypto.model.CryptoModel
import io.reactivex.Observable
import retrofit2.http.GET

interface CryptoAPI {

    @GET("markets?key=5ae606d668701c376c3111b9b7bb666fa4a4a744")
    fun getData() : Observable<List<CryptoModel>>
}