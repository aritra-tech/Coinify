package domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("symbol") val symbol: String? = null,
    @SerialName("slug") val slug: String? = null,
    @SerialName("cmc_rank") val cmcRank: Double? = null,
    @SerialName("num_market_pairs") val numMarketPairs: Int? = null,
    @SerialName("circulating_supply") val circulatingSupply: Double? = null,
    @SerialName("total_supply") val totalSupply: Double? = null,
    @SerialName("max_supply") val maxSupply: Double? = null,
    @SerialName("infinite_supply") val infiniteSupply: Boolean? = null,
    @SerialName("last_updated") val lastUpdated: String? = null,
    @SerialName("date_added") val dateAdded: String? = null,
    @SerialName("tags") val tags: List<String> = listOf(),
    @SerialName("platform") val platform: Platform? = null,
    @SerialName("self_reported_circulating_supply") val selfReportedCirculatingSupply: Double? = null,
    @SerialName("self_reported_market_cap") val selfReportedMarketCap: Double? = null,
    @SerialName("quote") val quote: Quote = Quote()
)
