package com.peod_stock.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peod_stock.model.ApiUser
import com.peod_stock.network.Resource
import com.peod_stock.network.RetrofitBuilder
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val userList: MutableLiveData<Resource<List<ApiUser>>> = MutableLiveData()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        userList.postValue(Resource.error(exception.message ?: "Something went wrong", null))
    }

    fun fetch() {
        userList.postValue(Resource.loading(null))
        viewModelScope.launch(exceptionHandler) {
            userList.postValue(Resource.success(RetrofitBuilder.apiService.getUsers()))
        }
    }
}