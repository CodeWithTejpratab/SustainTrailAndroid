package com.sustenanceandroid.model

abstract class Exposition

class ScoreInfo {
    var score: String? = null
    var scoreFactor: String? = null
    var category: String? = null
}

class ScoreData : Exposition() {
    var data: List<Float>? = null
    var scoreInfo: List<ScoreInfo>? = null

    val totalValue: String
        get() = (data?.sum()?.toInt() ?: 0).toString()
}

class LatestNews : Exposition() {
    var articles: List<Article>? = null
}

class Article {
    var url: String? = null
    var title: String? = null
    var category: String? = null
    var imageURL: String? = null
    var publish: String? = null
}
