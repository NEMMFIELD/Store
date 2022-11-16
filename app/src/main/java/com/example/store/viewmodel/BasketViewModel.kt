package com.example.store.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.store.model.Repository
import com.example.store.model.basket.BasketAdapter
import com.example.store.model.basket.BasketModel
import com.example.store.model.network.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(val repository: Repository) : ViewModel() {
    private val basketElements: MutableList<BasketModel> = ArrayList()
    val basketAdapter: BasketAdapter = BasketAdapter()

    private val _basketFlow: MutableStateFlow<State<List<BasketModel>>> =
        MutableStateFlow(State.Empty)
    val basketFlow: StateFlow<State<List<BasketModel>>> get() = _basketFlow

    init {
        addElementToBasket()
    }

    private fun addElementToBasket() {
        viewModelScope.launch {
            repository.getBasketResponse().collect {
                basketElements += it
                _basketFlow.value = State.Success(basketElements)
            }
        }
    }
}