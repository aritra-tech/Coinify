package data.remote

import com.aritra.coinify.BuildKonfig.API_KEY
import domain.model.Listings
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import utils.Constants.BASE_URL
import utils.Constants.REQUEST_TIME_OUT

object ApiClient {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(
                json = Json {
                    prettyPrint = true
                    isLenient = false
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                    explicitNulls = true
                }
            )
        }
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
        }
        install(HttpTimeout) {
            requestTimeoutMillis = REQUEST_TIME_OUT
            connectTimeoutMillis = REQUEST_TIME_OUT
            socketTimeoutMillis = REQUEST_TIME_OUT
        }
        defaultRequest {
            headers {
                append("X-CMC_PRO_API_KEY", API_KEY)
            }
        }
    }

    suspend fun getListings(): Listings {
        return client.get(BASE_URL + "cryptocurrency/listings/latest").body()
    }
}