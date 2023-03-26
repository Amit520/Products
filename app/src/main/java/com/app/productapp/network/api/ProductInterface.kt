package com.app.productapp.network.api

import com.app.productapp.model.MainModel
import com.app.productapp.network.Utils
import io.reactivex.Single
import retrofit2.http.GET

interface ProductInterface {

    @GET(Utils.URL_LIST_PRODUCT)
    fun getProductList(): Single<MainModel>

}