package domain.repository

import domain.model.crypto.Listings
import domain.model.news.News

interface CryptoAPI {
    suspend fun getListing(): Listings
    suspend fun getAllNews(): News
}