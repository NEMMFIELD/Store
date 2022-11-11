package com.example.store.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.store.model.Repository
import com.example.store.model.homestore.CarouselAdapter
import com.example.store.model.homestore.CarouselModel
import com.example.store.model.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeStoreViewModel @Inject constructor(private val repository: Repository):ViewModel() {
    var carouselAdapter = CarouselAdapter()

    private val _homeStoreFlow:MutableStateFlow<List<CarouselModel>> = MutableStateFlow(emptyList())
    val homeStoreFlow:StateFlow<List<CarouselModel>> get() = _homeStoreFlow

    init {
        loadStorePhones()
    }

    private fun loadStorePhones() {
        viewModelScope.launch {
            repository.getPhonesStore().collect {
                _homeStoreFlow.value = it
            }
        }
    }
}