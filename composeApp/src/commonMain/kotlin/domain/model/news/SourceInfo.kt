package domain.model.news

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SourceInfo (

    @SerialName("name" ) var name : String? = null,
    @SerialName("img"  ) var img  : String? = null,
    @SerialName("lang" ) var lang : String? = null

)