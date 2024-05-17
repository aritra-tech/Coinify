package di

import domain.repository.ListingRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import presentation.home.HomeViewModel
import utils.viewModelDefinition

val appModule = module {
    single {
        ListingRepository()
    }
    viewModelDefinition { HomeViewModel(get()) }

}