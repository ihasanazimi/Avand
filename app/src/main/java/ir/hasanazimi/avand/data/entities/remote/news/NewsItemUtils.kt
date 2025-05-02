package ir.hasanazimi.avand.data.entities.remote.news

import android.util.Log
import org.jsoup.Jsoup
import java.text.SimpleDateFormat
import java.util.Locale
import ir.hasanazimi.avand.data.entities.remote.news.khabar_online.Item as KhabarOnlineItem
import ir.hasanazimi.avand.data.entities.remote.news.zoomit.Item as ZoomitItem

object NewsItemUtils {
    fun getTitle(item: Any): String? = when (item) {
        is KhabarOnlineItem -> item.title
        is ZoomitItem -> item.title
        else -> null
    }

    fun getDescription(item: Any): String? = when (item) {
        is KhabarOnlineItem -> item.description
        is ZoomitItem -> cleanHtmlText(item.description?:"")
        else -> null
    }

    fun getLink(item: Any): String? = when (item) {
        is KhabarOnlineItem -> item.link
        is ZoomitItem -> item.link
        else -> null
    }

    fun getPublishDate(item: Any): String? = when (item) {
        is KhabarOnlineItem -> item.pubDate
        is ZoomitItem -> item.pubDate
        else -> null
    }?.let { formatDate(it) }

    fun getImageUrl(item: Any): String = when (item) {
        is KhabarOnlineItem -> item.enclosure?.url?:""
        is ZoomitItem -> item.enclosure?.url?:""
        else -> ""
    }.toString().also {
        Log.i("TAG", "getImageUrl: $it")
    }

    private fun formatDate(dateStr: String): String? {
        return try {
            val inputFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
            val outputFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("fa"))
            val date = inputFormat.parse(dateStr)
            date?.let { outputFormat.format(it) }
        } catch (e: Exception) {
            null
        }
    }
}


fun cleanHtmlText(html: String): String {
    return Jsoup.parse(html).text().trim()
}