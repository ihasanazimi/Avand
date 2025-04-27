package ir.hasanazimi.avand.data.entities.remote.news

import com.google.gson.annotations.SerializedName

data class NewsRSS(
    @SerializedName("guid")
    val guid: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("pubDate")
    val pubDate: String,
    @SerializedName("mediaThumbnail")
    val mediaThumbnail: String,
    @SerializedName("mediaContent")
    val mediaContent: String
)