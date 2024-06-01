package di

import domain.repository.ListingRepository
import org.koin.dsl.module
import presentation.HomeViewModel
import utils.viewModelDefinition

val appModule = module {
    single {
        ListingRepository()
    }
    viewModelDefinition { HomeViewModel(get()) }

}