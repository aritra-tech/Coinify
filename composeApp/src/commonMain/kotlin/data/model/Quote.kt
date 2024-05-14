package data.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Quote(
    @SerialName("USD") var USD: USD? = USD(),
    @SerialName("BTC") var BTC: BTC? = BTC()
)