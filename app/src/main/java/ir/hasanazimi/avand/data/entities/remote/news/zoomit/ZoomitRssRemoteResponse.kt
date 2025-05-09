package ir.hasanazimi.avand.data.entities.remote.news.zoomit


import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
data class RssFeed(
    @field:Element(name = "channel")
    var channel: Channel? = Channel(items = emptyList())
)

@Root(name = "channel", strict = false)
data class Channel(
    @field:Element(name = "title", required = false)
    var title: String? = null,

    @field:Element(name = "link", required = false)
    var link: String? = null,

    @field:Element(name = "description", required = false)
    var description: String? = null,

    @field:Element(name = "image", required = false)
    var image: ChannelImage? = null,

    @field:Element(name = "copyright", required = false)
    var copyright: String? = null,

    @field:Element(name = "lastBuildDate", required = false)
    var lastBuildDate: String? = null,

    @field:ElementList(inline = true, name = "item", required = false)
    var items: List<Item>? = null
)

@Root(name = "image", strict = false)
data class ChannelImage(
    @field:Element(name = "url", required = false)
    var url: String? = null,

    @field:Element(name = "title", required = false)
    var title: String? = null,

    @field:Element(name = "link", required = false)
    var link: String? = null,

    @field:Element(name = "width", required = false)
    var width: Int? = null,

    @field:Element(name = "height", required = false)
    var height: Int? = null
)

@Root(name = "item", strict = false)
data class Item(
    @field:Element(name = "title", required = false)
    var title: String? = null,

    @field:Element(name = "link", required = false)
    var link: String? = null,

    @field:Element(name = "description", required = false)
    var description: String? = null,

    @field:Element(name = "pubDate", required = false)
    var pubDate: String? = null,

    @field:Element(name = "dc:date", required = false)
    var dcDate: String? = null,

    @field:ElementList(entry = "category", inline = true, required = false)
    var categories: List<String>? = null,

    @field:Element(name = "guid", required = false)
    var guid: String? = null,

    @field:Element(name = "enclosure", required = false)
    var enclosure: Enclosure? = null
)

@Root(name = "enclosure", strict = false)
data class Enclosure(
    @field:Attribute(name = "url", required = false)
    var url: String? = null,

    @field:Attribute(name = "length", required = false)
    var length: String? = null,

    @field:Attribute(name = "type", required = false)
    var type: String? = null
)
