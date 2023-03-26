package com.app.productapp.model


import com.google.gson.annotations.SerializedName

data class MainModel(
    @SerializedName("limit")
    var limit: Int? = 0,
    @SerializedName("products")
    var products: ArrayList<ProductModel>? = arrayListOf(),
    @SerializedName("skip")
    var skip: Int? = 0,
    @SerializedName("total")
    var total: Int? = 0
) {
    data class ProductModel(
        @SerializedName("brand")
        var brand: String? = "",
        @SerializedName("category")
        var category: String? = "",
        @SerializedName("description")
        var description: String? = "",
        @SerializedName("discountPercentage")
        var discountPercentage: Double? = 0.0,
        @SerializedName("id")
        var id: Int? = 0,
        @SerializedName("images")
        var images: ArrayList<String>? = arrayListOf(),
        @SerializedName("price")
        var price: Int? = 0,
        @SerializedName("rating")
        var rating: Double? = 0.0,
        @SerializedName("stock")
        var stock: Int? = 0,
        @SerializedName("thumbnail")
        var thumbnail: String? = "",
        @SerializedName("title")
        var title: String? = ""
    )
}