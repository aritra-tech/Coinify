package presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import utils.AppPreferences

class SettingsViewModel(
    private val appPreferences: AppPreferences
): ViewModel() {

    private val _isDarkModeEnabled = MutableStateFlow(false)
    val isDarkModeEnabled = _isDarkModeEnabled.asStateFlow()

    init {
        isDarkModeEnabled()
    }

    private fun isDarkModeEnabled() = viewModelScope.launch(Dispatchers.IO) {
        _isDarkModeEnabled.value = appPreferences.isDarkModeEnabled()
    }

    fun changeDarkMode(isEnabled: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        appPreferences.changeDarkMode(isEnabled)
        _isDarkModeEnabled.value = isEnabled
    }
}