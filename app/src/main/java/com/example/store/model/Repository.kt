package com.example.store.model

import android.content.SharedPreferences
import com.example.store.model.bestseller.BestSellerModel
import com.example.store.model.details.CarouselDetailsModel
import com.example.store.model.homestore.CarouselModel
import com.example.store.model.network.PhonesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface Repository {
    fun getPhonesStore(): Flow<List<CarouselModel>>
    fun getPhonesBestSeller(): Flow<List<BestSellerModel>>
    fun setFavourites(bestSellerModel: BestSellerModel)
    fun getFavourites(id: Int?): Boolean
    fun getDetailsPhone(): Flow<CarouselDetailsModel>
}

class RepositoryImpl @Inject constructor(
    private val phonesApi: PhonesApi,
    private val sharedPreferences: SharedPreferences,
) : Repository {

    override fun getPhonesStore(): Flow<List<CarouselModel>> = flow {
        val request = phonesApi.getPhones().homeStore
        val myList: List<CarouselModel> =
            request?.map { convertResponseToCarouselModel(it!!) } ?: emptyList()
        emit(myList)
    }.flowOn(Dispatchers.IO)

    override fun getPhonesBestSeller(): Flow<List<BestSellerModel>> = flow {
        val request = phonesApi.getPhones().bestSeller
        val myList: List<BestSellerModel> =
            request?.map { convertResponseToBestSellerModel(it!!) } ?: emptyList()
        emit(myList)
    }.flowOn(Dispatchers.IO)

    override fun getDetailsPhone(): Flow<CarouselDetailsModel> = flow {
        emit(convertDetailsResponseToCarouselDetailsModel(phonesApi.getProductDetails()))
    }.flowOn(Dispatchers.IO)


    override fun setFavourites(bestSellerModel: BestSellerModel) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(bestSellerModel.id.toString(), bestSellerModel.isFavourites).apply()
    }

    override fun getFavourites(id: Int?): Boolean =
        sharedPreferences.getBoolean(id.toString(), false)

    private fun convertResponseToCarouselModel(response: HomeStoreItem): CarouselModel =
        CarouselModel(
            id = response.id,
            imagePath = response.picture,
            isNew = response.isNew,
            brand = response.title,
            description = response.subtitle,
            isBuy = response.isBuy
        )

    private fun convertResponseToBestSellerModel(response: BestSellerItem): BestSellerModel =
        BestSellerModel(
            id = response.id,
            isFavourites = getFavourites(response.id),
            title = response.title,
            discountPrice = response.discountPrice,
            priceWithoutDiscount = response.priceWithoutDiscount,
            picturePath = response.picture
        )

    private fun convertDetailsResponseToCarouselDetailsModel(responseDetails: ResponseDetails): CarouselDetailsModel =
        CarouselDetailsModel(detailsImg = responseDetails.images,
            phoneRating = responseDetails.rating as Double,
            cpu = responseDetails.cPU,
            camera = responseDetails.camera,
            ssd = responseDetails.ssd,
            sd = responseDetails.sd,
            title = responseDetails.title)

}