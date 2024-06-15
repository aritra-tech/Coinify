package di

import domain.repository.ListingRepository
import org.koin.dsl.module
import presentation.HomeViewModel
import presentation.SettingsViewModel
import utils.ThemeViewModel
import utils.coreComponent
import utils.viewModelDefinition

val appModule = module {

    single { ListingRepository() }
    single { coreComponent.appPreferences }

    viewModelDefinition { HomeViewModel(get()) }
    viewModelDefinition { SettingsViewModel(get()) }
    viewModelDefinition { ThemeViewModel(get()) }
}