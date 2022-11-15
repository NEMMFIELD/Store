package com.example.store.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.store.model.Repository
import com.example.store.model.basket.BasketAdapter
import com.example.store.model.basket.BasketModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(val repository: Repository) : ViewModel() {
    val basketElements: MutableList<BasketModel> = ArrayList()
    val basketAdapter: BasketAdapter = BasketAdapter()
    private val _basketFlow: MutableStateFlow<List<BasketModel>> = MutableStateFlow(emptyList())
    val basketFlow: StateFlow<List<BasketModel>> get() = _basketFlow

    init {
        addElementToBasket()
    }
    private fun addElementToBasket() {
        viewModelScope.launch {
            repository.getBasketResponse().collect {
                basketElements += it
                _basketFlow.value = basketElements
            }
        }
    }
}