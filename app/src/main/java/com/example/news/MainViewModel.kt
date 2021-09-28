package com.example.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.NewsList
import com.example.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    private val _topHeadlines: MutableLiveData<com.example.utils.Resource<NewsList>> =
        MutableLiveData()
    val topHeadlines: LiveData<com.example.utils.Resource<NewsList>> get() = _topHeadlines

//    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
//        _topHeadlines.postValue(Resource.error(exception.message ?: "Something went wrong", null))
//    }

    fun fetchTopHeadlines(country:String?=null) {
        Timber.d("fetchTopHeadlines")
        _topHeadlines.postValue(com.example.utils.Resource.Loading(null))
        viewModelScope.launch {
            _topHeadlines.postValue(repository.getNewsHeadLines(country))
        }

    }
}