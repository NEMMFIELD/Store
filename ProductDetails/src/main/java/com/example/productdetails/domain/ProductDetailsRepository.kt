package com.example.productdetails.data

import android.content.SharedPreferences
import com.example.productdetails.data.network.ProductDetailsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface ProductDetailsRepository {
    fun getDetailsPhone(): Flow<CarouselDetailsModel>
}

class ProductDetailsRepositoryImpl @Inject constructor(
    private val phonesApi: ProductDetailsApi,
) : ProductDetailsRepository {

    override fun getDetailsPhone(): Flow<CarouselDetailsModel> = flow {
        emit(convertDetailsResponseToCarouselDetailsModel(phonesApi.getProductDetails()))
    }.flowOn(Dispatchers.IO)

    private fun convertDetailsResponseToCarouselDetailsModel(responseDetails: ResponseDetails): CarouselDetailsModel =
        CarouselDetailsModel(detailsImg = responseDetails.images,
            phoneRating = responseDetails.rating as Double,
            cpu = responseDetails.cPU,
            camera = responseDetails.camera,
            ssd = responseDetails.ssd,
            sd = responseDetails.sd,
            title = responseDetails.title)

}