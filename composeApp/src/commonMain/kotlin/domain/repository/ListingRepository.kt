package domain.repository

import domain.model.Listings
import data.remote.ApiClient

class ListingRepository: CryptoDao {
    override suspend fun getListing(): Listings {
        return ApiClient.getListings()
    }
}