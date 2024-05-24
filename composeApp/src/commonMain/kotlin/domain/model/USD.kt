package domain.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class USD(
    @SerialName("price") val price: Double? = null,
    @SerialName("volume_24h") val volume24h: Double? = null,
    @SerialName("volume_change_24h") val volumeChange24h: Double? = null,
    @SerialName("percent_change_1h") val percentChange1h: Double? = null,
    @SerialName("percent_change_24h") val percentChange24h: Double? = null,
    @SerialName("percent_change_7d") val percentChange7d: Double? = null,
    @SerialName("market_cap") val marketCap: Double? = null,
    @SerialName("market_cap_dominance") val marketCapDominance: Double? = null,
    @SerialName("fully_diluted_market_cap") val fullyDilutedMarketCap: Double? = null,
    @SerialName("last_updated") val lastUpdated: String? = null
)