package data.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Status(
    @SerialName("timestamp") var timestamp: String? = null,
    @SerialName("error_code") var errorCode: Int? = null,
    @SerialName("error_message") var errorMessage: String? = null,
    @SerialName("elapsed") var elapsed: Int? = null,
    @SerialName("credit_count") var creditCount: Int? = null
)