package com.sanjay.newsapp.data.model

import com.squareup.moshi.Json
import java.io.Serializable

data class NewsModel(
    @Json(name = "status") var status: String,
    @Json(name = "totalResults") var totalResults: Int,
    @Json(name = "articles") var articles: List<Articles>
) : Serializable

data class Source(
    @Json(name = "id") var id: String,
    @Json(name = "name") var name: String
)

data class Articles(
    @Json(name = "source") var source: Source,
    @Json(name = "author") var author: String,
    @Json(name = "title") var title: String,
    @Json(name = "description") var description: String,
    @Json(name = "url") var url: String,
    @Json(name = "urlToImage") var urlToImage: String,
    @Json(name = "publishedAt") var publishedAt: String,
    @Json(name = "content") var content: String
)
