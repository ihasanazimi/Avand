package ir.hasanazimi.avand.data.entities.remote.news.khabar_online

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
data class RssFeed(
    @field:Element(name = "channel")
    var channel: Channel? = null
)

@Root(name = "channel", strict = false)
data class Channel(
    @field:Element(name = "title", required = false)
    var title: String? = null,

    @field:Element(name = "link", required = false)
    var link: String? = null,

    @field:Element(name = "description", required = false)
    var description: String? = null,

    @field:Element(name = "language", required = false)
    var language: String? = null,

    @field:Element(name = "copyright", required = false)
    var copyright: String? = null,

    @field:Element(name = "lastBuildDate", required = false)
    var lastBuildDate: String? = null,

    @field:Element(name = "generator", required = false)
    var generator: String? = null,

    @field:ElementList(inline = true, name = "item", required = false)
    var items: List<Item>? = null
)

@Root(name = "item", strict = false)
data class Item(
    @field:Element(name = "title", required = false)
    var title: String? = null,

    @field:Element(name = "link", required = false)
    var link: String? = null,

    @field:Element(name = "description", required = false)
    var description: String? = null,

    @field:Element(name = "content:encoded", required = false)
    var contentEncoded: String? = null,

    @field:Element(name = "source", required = false)
    var source: Source? = null,

    @field:Element(name = "enclosure", required = false)
    var enclosure: Enclosure? = null,

    @field:Element(name = "category", required = false)
    var category: Category? = null,

    @field:Element(name = "pubDate", required = false)
    var pubDate: String? = null,

    @field:Element(name = "guid", required = false)
    var guid: String? = null
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

@Root(name = "category", strict = false)
data class Category(
    @field:Attribute(name = "domain", required = false)
    var domain: String? = null,

    @field:Element(data = true, required = false)
    var value: String? = null
)

@Root(name = "source", strict = false)
data class Source(
    @field:Attribute(name = "url", required = false)
    var url: String? = null,

    @field:Element(data = true, required = false)
    var value: String? = null
)