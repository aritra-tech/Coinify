package domain.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Status(
    @SerialName("timestamp") val timestamp: String? = null,
    @SerialName("error_code") val errorCode: Int? = null,
    @SerialName("error_message") val errorMessage: String? = null,
    @SerialName("elapsed") val elapsed: Int? = null,
    @SerialName("credit_count") val creditCount: Int? = null
)