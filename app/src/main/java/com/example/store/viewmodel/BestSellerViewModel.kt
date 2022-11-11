package com.example.store.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.store.model.Repository
import com.example.store.model.bestseller.BestSellerAdapter
import com.example.store.model.bestseller.BestSellerModel
import com.example.store.model.homestore.CarouselModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BestSellerViewModel @Inject constructor(private val repository: Repository): ViewModel() {


    private val _bestSellerFlow: MutableStateFlow<List<BestSellerModel>> = MutableStateFlow(emptyList())
    val bestSellerFlow: StateFlow<List<BestSellerModel>> get() = _bestSellerFlow

    init {
        loadBestSellersPhones()
    }

    private fun loadBestSellersPhones() = viewModelScope.launch {
        repository.getPhonesBestSeller().collect{
            _bestSellerFlow.value = it
        }
    }

    fun setLike(bestSellerModel: BestSellerModel) {
        repository.setFavourites(bestSellerModel)
    }

}