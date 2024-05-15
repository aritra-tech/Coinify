package data.repository

import data.model.Listings

interface CryptoApi {
    suspend fun getListing(): Listings
}