package domain.repository

import domain.model.Listings
import data.remote.ApiClient

class ListingRepository: CryptoApi {
    override suspend fun getListing(): Listings {
        return ApiClient.getListings()
    }
}