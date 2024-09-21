package com.example.eshop.data.categories

data class Data(
    val id: Int?,
    val name: String?,
    val priority: Any?,
    val status: Int?,
    val sub_categories: List<SubCategory?>?
)