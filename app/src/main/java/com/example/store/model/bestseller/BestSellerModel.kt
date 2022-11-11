package com.example.store.model.bestseller

data class BestSellerModel(
    val id: Int?,
    var isFavourites:Boolean,
    val title:String?,
    val discountPrice:Int?,
    val priceWithoutDiscount:Int?,
    val picturePath:String?
)