package presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.remote.Resources
import domain.model.Listings
import domain.repository.ListingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: ListingRepository
): ViewModel() {

    private val _latestListing = MutableStateFlow<Resources<Listings>>(Resources.LOADING)
    var latestListing: StateFlow<Resources<Listings>> = _latestListing.asStateFlow()

    fun getLatestListing() {
        viewModelScope.launch(Dispatchers.IO) {
            _latestListing.value = Resources.LOADING
            try {
                val response = repository.getListing()
                _latestListing.value = Resources.SUCCESS(response)
            } catch (e: Exception) {
                _latestListing.value = Resources.ERROR(e.message.toString())
            }
        }
    }
}