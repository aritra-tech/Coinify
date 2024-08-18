package di

import com.aritra.coinify.BuildKonfig.API_KEY
import data.remote.ApiClient
import domain.repository.CoinifyRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import presentation.home.HomeViewModel
import presentation.news.NewsViewModel
import presentation.settings.SettingsViewModel
import utils.Constants.REQUEST_TIME_OUT
import utils.ThemeViewModel
import utils.coreComponent
import utils.viewModelDefinition

@OptIn(ExperimentalSerializationApi::class)
val appModule = module {
    single {
        HttpClient {
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
//                filter { filter-> filter.url.host.contains("coinmarketcap.com") }
//                sanitizeHeader { header-> header == HttpHeaders.Authorization }
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
    }
    single { CoinifyRepository(get()) }
    single { ApiClient(get()) }
    single { coreComponent.appPreferences }

    viewModelDefinition { HomeViewModel(get()) }
    viewModelDefinition { SettingsViewModel(get()) }
    viewModelDefinition { ThemeViewModel(get()) }
    viewModelDefinition { NewsViewModel(get()) }
}