package com.example.basket.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basket.data.BasketAdapter
import com.example.basket.data.BasketModel
import com.example.basket.BasketRepository
import com.example.basket.network.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(val basketRepository: BasketRepository) : ViewModel() {
    private val basketElements: MutableList<BasketModel> = ArrayList()
    val basketAdapter: BasketAdapter = BasketAdapter()

    private val _basketFlow: MutableStateFlow<State<List<BasketModel>>> =
        MutableStateFlow(State.Empty)
    val basketFlow: StateFlow<State<List<BasketModel>>> get() = _basketFlow

    init {
        addElementToBasket()
    }/**/

    private fun addElementToBasket() {
        viewModelScope.launch {
            basketRepository.getBasketResponse().collect {
                basketElements += it
                _basketFlow.value = State.Success(basketElements)
            }
        }
    }
}