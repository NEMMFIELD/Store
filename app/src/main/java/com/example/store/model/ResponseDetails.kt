package com.example.store.model

import com.squareup.moshi.Json

data class ResponseDetails(

	@Json(name="sd")
	val sd: String? = null,

	@Json(name="images")
	val images: List<String?>? = null,

	@Json(name="color")
	val color: List<String?>? = null,

	@Json(name="ssd")
	val ssd: String? = null,

	@Json(name="price")
	val price: Int? = null,

	@Json(name="rating")
	val rating: Any? = null,

	@Json(name="CPU")
	val cPU: String? = null,

	@Json(name="isFavorites")
	val isFavorites: Boolean? = null,

	@Json(name="id")
	val id: String? = null,

	@Json(name="camera")
	val camera: String? = null,

	@Json(name="title")
	val title: String? = null,

	@Json(name="capacity")
	val capacity: List<String?>? = null
)
