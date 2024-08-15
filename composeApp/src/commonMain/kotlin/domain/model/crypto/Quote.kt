package domain.model.crypto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Quote(
    @SerialName("USD") val USD: USD = USD(),
    @SerialName("BTC") val BTC: BTC = BTC()
)