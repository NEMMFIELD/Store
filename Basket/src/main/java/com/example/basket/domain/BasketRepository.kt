package com.example.basket

import com.example.basket.data.BasketModel
import com.example.basket.network.BasketApi
import com.example.store.model.BasketItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface BasketRepository {
    fun getBasketResponse(): Flow<List<BasketModel>>
}

class BasketRepositoryImpl @Inject constructor(
    private val phonesApi: BasketApi,
) : BasketRepository {


    override fun getBasketResponse(): Flow<List<BasketModel>> = flow {
        val request = phonesApi.getBasket().basket ?: throw Exception()
        emit(request.map { convertBasketResponseToBasketModel(it ?: throw Exception()) })

    }.flowOn(Dispatchers.IO)

    private fun convertBasketResponseToBasketModel(responseBasket: BasketItem): BasketModel =
        BasketModel(id = responseBasket.id,
            image = responseBasket.images,
            title = responseBasket.title,
            price = responseBasket.price
        )
}