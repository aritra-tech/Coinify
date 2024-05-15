package domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("id") var id: Int? = null,
    @SerialName("name") var name: String? = null,
    @SerialName("symbol") var symbol: String? = null,
    @SerialName("slug") var slug: String? = null,
    @SerialName("cmc_rank") var cmcRank: Int? = null,
    @SerialName("num_market_pairs") var numMarketPairs: Int? = null,
    @SerialName("circulating_supply") var circulatingSupply: Int? = null,
    @SerialName("total_supply") var totalSupply: Int? = null,
    @SerialName("max_supply") var maxSupply: Int? = null,
    @SerialName("infinite_supply") var infiniteSupply: Boolean? = null,
    @SerialName("last_updated") var lastUpdated: String? = null,
    @SerialName("date_added") var dateAdded: String? = null,
    @SerialName("tags") var tags: List<String> = listOf(),
    @SerialName("platform") var platform: String? = null,
    @SerialName("self_reported_circulating_supply") var selfReportedCirculatingSupply: String? = null,
    @SerialName("self_reported_market_cap") var selfReportedMarketCap: String? = null,
    @SerialName("quote") var quote: Quote? = Quote()

)