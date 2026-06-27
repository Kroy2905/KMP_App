package com.kroy.kmp_project.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kroy.kmp_project.data.model.Article
import com.kroy.kmp_project.utils.Resource
import com.kroy.kmp_project.utils.articles
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel  : ViewModel(){
    private val _newsStateFlow = MutableStateFlow<Resource<List<Article>> >(Resource.Idle)
    val newsStateFlow : StateFlow<Resource<List<Article>>>
        get() = _newsStateFlow

    init {
       // getHeadline()
    }
     fun searchQueryNews(query: String){
        viewModelScope.launch() {
            _newsStateFlow.emit(Resource.Loading)
            // delay(5000)
            try {
                //   val x = "%".toInt()
                val articleList = articles
                _newsStateFlow.emit(Resource.Success(articleList))
            }catch (e: Exception){
                _newsStateFlow.emit(Resource.Error(e.message.toString()))
            }
        }

    }

}