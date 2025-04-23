package ir.hasanazimi.avand.data.entities.remote.news

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import org.simpleframework.xml.Text


/*

data class GoogleSearchResponse(
    @SerializedName("items")
    val items: List<SearchResult>?
)

data class SearchResult(
    @SerializedName("title")
    val title: String,

    @SerializedName("link")
    val link: String,

    @SerializedName("snippet")
    val snippet: String
)
*/


@Root(name = "rss", strict = false)
data class GoogleNewsRss(
    @field:Element(name = "channel")
    val channel: Channel
)

@Root(name = "channel", strict = false)
data class Channel(
    @field:ElementList(inline = true, entry = "item")
    val items: List<NewsItem>
)

@Root(name = "item", strict = false)
data class NewsItem(
    @field:Element(name = "title")
    val title: String,

    @field:Element(name = "link")
    val link: String,

    @field:Element(name = "guid", required = false)
    val guid: Guid? = null,

    @field:Element(name = "pubDate", required = false)
    val pubDate: String? = null,

    @field:Element(name = "description", required = false)
    val description: String? = null,

    @field:Element(name = "source", required = false)
    val source: Source? = null
)

@Root(name = "guid", strict = false)
data class Guid(
    @field:Attribute(name = "isPermaLink", required = false)
    val isPermaLink: Boolean = false,

    @field:Text(required = false)
    val value: String? = null
)

@Root(name = "source", strict = false)
data class Source(
    @field:Attribute(name = "url", required = false)
    val url: String? = null,

    @field:Text(required = false)
    val value: String? = null
)