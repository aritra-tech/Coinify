package domain.model.crypto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class USD(
    @SerialName("price") val price: Double? = null,
    @SerialName("volume_24h") val volume24h: Double? = null,
    @SerialName("volume_change_24h") val volumeChange24h: Double? = null,
    @SerialName("percent_change_1h") val percentChange1h: Double = 0.0,
    @SerialName("percent_change_24h") val percentChange24h: Double = 0.0,
    @SerialName("percent_change_7d") val percentChange7d: Double = 0.0,
    @SerialName("percent_change_30d") val percentChange30d: Double = 0.0,
    @SerialName("percent_change_60d") val percentChange60d: Double = 0.0,
    @SerialName("percent_change_90d") val percentChange90d: Double = 0.0,
    @SerialName("market_cap") val marketCap: Double? = null,
    @SerialName("market_cap_dominance") val marketCapDominance: Double? = null,
    @SerialName("fully_diluted_market_cap") val fullyDilutedMarketCap: Double? = null,
    @SerialName("last_updated") val lastUpdated: String? = null
)