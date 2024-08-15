package presentation.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.remote.Resources
import domain.model.news.News
import domain.repository.CoinifyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsViewModel(
    private val repository: CoinifyRepository
): ViewModel() {

    private val _allNews = MutableStateFlow<Resources<News>>(Resources.LOADING)
    var allNews: StateFlow<Resources<News>> = _allNews.asStateFlow()

    fun getAllNews() {
        viewModelScope.launch {
            _allNews.value = Resources.LOADING
            try {
                val response = repository.getAllNews()
                _allNews.value = Resources.SUCCESS(response)
            } catch (e: Exception) {
                _allNews.value = Resources.ERROR(e.message.toString())
            }
        }
    }
}