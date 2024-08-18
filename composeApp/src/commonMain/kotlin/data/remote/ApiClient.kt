package data.remote

import domain.model.crypto.Listings
import domain.model.news.News
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import utils.Constants.BASE_URL
import utils.Constants.NEWS_URL

class ApiClient(private val client: HttpClient) {

    suspend fun getListings(): Listings {
        return client.get(BASE_URL + "cryptocurrency/listings/latest").body()
    }

    suspend fun getAllNews(): News {
        return client.get(NEWS_URL + "v2/news/?lang=EN").body()
    }
}