package com.example.mainscreen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mainscreen.data.network.State
import com.example.mainscreen.data.BestSellerModel
import com.example.store.model.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BestSellerViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _bestSellerFlow: MutableStateFlow<State<List<BestSellerModel>>> =
        MutableStateFlow(State.Empty)
    val bestSellerFlow: StateFlow<State<List<BestSellerModel>>> get() = _bestSellerFlow

    init {
        loadBestSellersPhones()
    }

    private fun loadBestSellersPhones() = viewModelScope.launch {
        repository.getPhonesBestSeller().collect {
            _bestSellerFlow.value = State.Success(it)
        }
    }

    fun setLike(bestSellerModel: BestSellerModel) {
        repository.setFavourites(bestSellerModel)
    }

}