package domain.model.news

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data (

    @SerialName("id") var id: String?     = null,
    @SerialName("guid") var guid: String?     = null,
    @SerialName("published_on") var publishedOn: Int?        = null,
    @SerialName("imageurl") var imageurl: String?     = null,
    @SerialName("title") var title: String?     = null,
    @SerialName("url") var url: String?     = null,
    @SerialName("body") var body: String?     = null,
    @SerialName("tags") var tags: String?     = null,
    @SerialName("lang") var lang: String?     = null,
    @SerialName("upvotes") var upvotes: String?     = null,
    @SerialName("downvotes") var downvotes: String?     = null,
    @SerialName("categories") var categories: String?     = null,
    @SerialName("source_info") var sourceInfo: SourceInfo? = SourceInfo(),
    @SerialName("source") var source: String?     = null

)