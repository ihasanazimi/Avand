package ir.hasanazimi.avand.data.entities.remote.news.egtesad_online

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

    @field:Element(name = "managingEditor", required = false)
    var managingEditor: String? = null,

    @field:Element(name = "webMaster", required = false)
    var webMaster: String? = null,

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

    @field:Element(name = "author", required = false)
    var author: String? = null,

    @field:Element(name = "enclosure", required = false)
    var enclosure: Enclosure? = null,

    @field:Element(name = "pubDate", required = false)
    var pubDate: String? = null
)

@Root(name = "enclosure", strict = false)
data class Enclosure(
    @field:Attribute(name = "url", required = false)
    var url: String? = null,

    @field:Attribute(name = "type", required = false)
    var type: String? = null,

    @field:Attribute(name = "length", required = false)
    var length: String? = null
)