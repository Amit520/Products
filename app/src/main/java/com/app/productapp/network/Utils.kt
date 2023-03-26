package com.app.productapp.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object Utils {

    const val BASE_URL = "https://dummyjson.com/"
    const val URL_LIST_PRODUCT = "products"
    const val URL_ADD_PRODUCT = "product/add"
    
    fun hasInternetConnection(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}