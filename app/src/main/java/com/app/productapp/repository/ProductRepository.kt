package com.app.productapp.repository

import com.app.productapp.model.MainModel
import com.app.productapp.network.api.ProductInterface
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProductRepository @Inject constructor(private val api: ProductInterface) {

    fun fetchWeatherRecords(): Single<MainModel> {
        return api.getProductList().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}