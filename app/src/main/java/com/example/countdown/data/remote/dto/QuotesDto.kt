package com.example.countdown.data.remote.dto

import com.example.countdown.domain.model.QuotesModel

data class QuotesDto(
    val _id: String,
    val author: String,
    val authorSlug: String,
    val content: String,
    val dateAdded: String,
    val dateModified: String,
    val length: Int,
    val tags: List<String>
) {
    fun toQuotesModel() = QuotesModel(
        content = content,
        author = author
    )
}