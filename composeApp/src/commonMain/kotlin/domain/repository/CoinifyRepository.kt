package domain.repository

import data.remote.ApiClient
import domain.model.crypto.Listings
import domain.model.news.News

class CoinifyRepository(
    private val apiClient: ApiClient
): CryptoAPI  {

    override suspend fun getListing(): Listings {
        return apiClient.getListings()
    }

    override suspend fun getAllNews(): News {
        return apiClient.getAllNews()
    }
}