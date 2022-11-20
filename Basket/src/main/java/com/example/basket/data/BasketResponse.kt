package com.example.store.model

import com.squareup.moshi.Json

data class BasketResponse(

	@Json(name="basket")
	val basket: List<BasketItem?>? = null,

	@Json(name="delivery")
	val delivery: String? = null,

	@Json(name="total")
	val total: Int? = null,

	@Json(name="id")
	val id: String? = null
)

data class BasketItem(

	@Json(name="images")
	val images: String? = null,

	@Json(name="price")
	val price: Int? = null,

	@Json(name="id")
	val id: Int? = null,

	@Json(name="title")
	val title: String? = null
)
