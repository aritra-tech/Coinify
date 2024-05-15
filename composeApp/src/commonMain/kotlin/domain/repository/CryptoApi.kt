package domain.repository

import domain.model.Listings

interface CryptoApi {
    suspend fun getListing(): Listings
}