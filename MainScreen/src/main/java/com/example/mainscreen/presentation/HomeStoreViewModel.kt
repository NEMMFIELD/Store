package com.example.mainscreen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mainscreen.data.network.State
import com.example.mainscreen.data.CarouselAdapter
import com.example.mainscreen.data.CarouselModel
import com.example.store.model.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeStoreViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    var carouselAdapter = CarouselAdapter()

    private val _homeStoreFlow: MutableStateFlow<State<List<CarouselModel>>> =
        MutableStateFlow(State.Empty)
    val homeStoreFlow: StateFlow<State<List<CarouselModel>>> get() = _homeStoreFlow

    init {
        loadStorePhones()
    }

    private fun loadStorePhones() {
        viewModelScope.launch {
            repository.getPhonesStore().collect {
                _homeStoreFlow.value = State.Success(it)
            }
        }
    }
}