package presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.remote.Resources
import domain.model.Data
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

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _filteredListing = MutableStateFlow<List<Data>>(emptyList())
    val filteredListing: StateFlow<List<Data>> = _filteredListing.asStateFlow()

    fun getLatestListing() {
        viewModelScope.launch(Dispatchers.IO) {
            _latestListing.value = Resources.LOADING
            try {
                val response = repository.getListing()
                _latestListing.value = Resources.SUCCESS(response)
                filterData(response.data, "")
            } catch (e: Exception) {
                _latestListing.value = Resources.ERROR(e.message.toString())
            }
        }
    }

    private fun filterData(data: List<Data>, query: String) {
        _filteredListing.value = data.filter { item ->
            item.name.contains(query, ignoreCase = true)
        }
    }

    fun  updateSearchData(query: String) {
        _searchQuery.value = query
        _latestListing.value.let { resources ->
            if (resources is Resources.SUCCESS) {
                filterData(resources.response.data, query)
            }
        }
    }
}