package domain.repository

import domain.model.Listings

interface CryptoDao {
    suspend fun getListing(): Listings
}