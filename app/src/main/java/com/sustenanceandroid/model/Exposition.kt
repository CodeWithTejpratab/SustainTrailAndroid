package com.sustenanceandroid.model

import com.google.gson.annotations.SerializedName

abstract class Exposition

class ScoreInfo {
    @SerializedName("score")
    var score: String? = null

    @SerializedName("score_factor")
    var scoreFactor: String? = null

    @SerializedName("category")
    var category: String? = null
}

class ScoreData : Exposition() {
    var data: List<Float>? = null
    var scoreInfo: List<ScoreInfo>? = null

    val totalValue: String
        get() = (data?.sum()?.toInt() ?: 0).toString()
}

class NewsSection : Exposition() {
    var header: String? = null
    var articles: List<Article>? = null
}

class Article {
    @SerializedName("url")
    var url: String? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("category")
    var category: String? = null

    @SerializedName("image_url")
    var imageURL: String? = null

    @SerializedName("published")
    var publish: String? = null
}

class AdPlus : Exposition() {
    var header: String? = null
    var category: String? = null
    var images: List<String>? = null
}

class EventSection : Exposition() {
    var events: List<Event>? = null
}

class Event {
    @SerializedName("image_url")
    var imageUrl: String? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("url")
    var url: String? = null

    @SerializedName("date")
    var date: String? = null

    @SerializedName("total_spots")
    var totalSpots: Int? = null

    @SerializedName("remaining_spots")
    var remainingSpots: Int? = null
}
