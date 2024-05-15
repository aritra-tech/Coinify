package domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BTC(
    @SerialName("price") var price: Int? = null,
    @SerialName("volume_24h") var volume24h: Int? = null,
    @SerialName("volume_change_24h") var volumeChange24h: Int? = null,
    @SerialName("percent_change_1h") var percentChange1h: Int? = null,
    @SerialName("percent_change_24h") var percentChange24h: Int? = null,
    @SerialName("percent_change_7d") var percentChange7d: Int? = null,
    @SerialName("market_cap") var marketCap: Int? = null,
    @SerialName("market_cap_dominance") var marketCapDominance: Int? = null,
    @SerialName("fully_diluted_market_cap") var fullyDilutedMarketCap: Double? = null,
    @SerialName("last_updated") var lastUpdated: String? = null
)