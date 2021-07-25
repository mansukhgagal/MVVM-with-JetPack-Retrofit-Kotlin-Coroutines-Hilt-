package com.example.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.ApiUser
import com.example.network.Resource
import com.example.network.RetrofitBuilder
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _userList: MutableLiveData<Resource<List<ApiUser>>> = MutableLiveData()
    val userList:LiveData<Resource<List<ApiUser>>> get() = _userList

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _userList.postValue(Resource.error(exception.message ?: "Something went wrong", null))
    }

    fun fetch() {
        _userList.postValue(Resource.loading(null))
        viewModelScope.launch(exceptionHandler) {
            _userList.postValue(Resource.success(RetrofitBuilder.apiService.getUsers()))
        }
    }
}