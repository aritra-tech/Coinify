package domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Listings(
    @SerialName("data")
    val data: List<Data> = listOf(),
    @SerialName("status")
    val status: Status = Status()
)
