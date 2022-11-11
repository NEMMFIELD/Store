package com.example.store.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.store.model.Repository
import com.example.store.model.RepositoryImpl
import com.example.store.model.details.CarouselDetailsAdapter
import com.example.store.model.details.CarouselDetailsModel
import com.example.store.model.homestore.CarouselAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(private val repository: RepositoryImpl) : ViewModel() {
    var carouselDetailsAdapter = CarouselDetailsAdapter()
    private val _detailsImageFlow: MutableStateFlow<List<CarouselDetailsModel>> = MutableStateFlow(emptyList())
    val detailsImageFlow: StateFlow<List<CarouselDetailsModel>> get() = _detailsImageFlow

    init {
        loadDetailsImage()
    }

    private fun loadDetailsImage() {
        viewModelScope.launch {
            repository.getDetailsPhone().collect {
                _detailsImageFlow.value = it
            }
        }
    }
}