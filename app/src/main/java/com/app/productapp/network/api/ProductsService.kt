package com.app.productapp.network.api

import com.app.productapp.model.MainModel
import io.reactivex.Single
import javax.inject.Inject

class ProductsService @Inject constructor(private val productInterface: ProductInterface) {

    fun getCountries(): Single<MainModel> {
        return productInterface.getProductList()
    }
}