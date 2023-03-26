package com.app.productapp.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.productapp.model.MainModel
import com.app.productapp.network.NetworkResponse
import com.app.productapp.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val productsRepository: ProductRepository) :
    ViewModel() {

    private val _response: MutableLiveData<NetworkResponse<MainModel>> = MutableLiveData()
    val response: LiveData<NetworkResponse<MainModel>> = _response

    private val disposable = CompositeDisposable()

    fun getProductList() = fetchProductList()

    private fun fetchProductList() {
        _response.postValue(NetworkResponse.Loading())
        disposable.add(
            productsRepository.fetchWeatherRecords()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MainModel>() {
                    override fun onSuccess(value: MainModel) {
                        _response.postValue(NetworkResponse.Success(value))
                    }

                    override fun onError(e: Throwable) {
                        _response.postValue(NetworkResponse.Error(e.message.toString()))
                    }
                })
        )

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}