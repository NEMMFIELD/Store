package com.example.mainscreen.data

import com.squareup.moshi.Json

data class Response(

    @Json(name="best_seller")
	val bestSeller: List<BestSellerItem?>? = null,

    @Json(name="home_store")
	val homeStore: List<HomeStoreItem?>? = null
)

data class BestSellerItem(

	@Json(name="is_favorites")
	val isFavorites: Boolean? = null,

	@Json(name="discount_price")
	val discountPrice: Int? = null,

	@Json(name="id")
	val id: Int? = null,

	@Json(name="title")
	val title: String? = null,

	@Json(name="price_without_discount")
	val priceWithoutDiscount: Int? = null,

	@Json(name="picture")
	val picture: String? = null
)

data class HomeStoreItem(

	@Json(name="subtitle")
	val subtitle: String? = null,

	@Json(name="id")
	val id: Int? = null,

	@Json(name="title")
	val title: String? = null,

	@Json(name="picture")
	val picture: String? = null,

	@Json(name="is_buy")
	val isBuy: Boolean? = null,

	@Json(name="is_new")
	val isNew: Boolean? = null
)
