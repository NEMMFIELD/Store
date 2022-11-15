package com.example.store.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.store.model.Repository
import com.example.store.model.details.CarouselDetailsAdapter
import com.example.store.model.details.CarouselDetailsModel
import com.example.store.model.network.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {
    private val _detailsImageFlow: MutableStateFlow<State<CarouselDetailsModel>> =
        MutableStateFlow(State.Empty)
    val detailsImageFlow: StateFlow<State<CarouselDetailsModel>> get() = _detailsImageFlow

    var carouselDetailsAdapter = CarouselDetailsAdapter()

    init {
        loadDetailsImage()
    }

    private fun loadDetailsImage() {
        viewModelScope.launch {
            repository.getDetailsPhone().collect {
                _detailsImageFlow.value = State.Success(it)
            }
        }
    }

}