package domain.model.news

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class News (

    @SerialName("Type") var Type: Int? = null,
    @SerialName("Message") var Message: String? = null,
    @SerialName("Promoted") var Promoted: List<String> = listOf(),
    @SerialName("Data") var Data: List<Data> = listOf(),
    @SerialName("RateLimit") var RateLimit: RateLimit? = RateLimit(),
    @SerialName("HasWarning") var HasWarning: Boolean? = null

)
