package com.app.productapp.network

import retrofit2.Response
import java.net.UnknownHostException

open class BaseRepository {

    suspend fun <T> makeNetworkRequest(request: suspend () -> Response<T>): NetworkResponse<T> {
        return try {
            val response = request.invoke()
            if (response.isSuccessful) {
                val responseBody = response.body()
                NetworkResponse.Success(responseBody)
            } else {
                NetworkResponse.Error(response.errorBody()?.string()!!)
            }
        } catch (e1: Exception) {
            if (e1 is UnknownHostException) {
                NetworkResponse.Error("Internet connection is required.....!")
            } else {
                NetworkResponse.Error(e1.localizedMessage!!)
            }
        }
    }

}