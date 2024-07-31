package presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.database.CoinifyDatabase
import data.repository.CoinifyRepository
import domain.model.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class DetailsScreenViewModel(
    database: CoinifyDatabase
): ViewModel() {

    private val repository = CoinifyRepository(database.coinifyDao())
    var isBookmarked by mutableStateOf(false)

    fun isCoinBookmarked(data: Data) {
        viewModelScope.launch(Dispatchers.IO) {
            data.id.let {
                repository.getCoin(it)?.let {
                    isBookmarked = true
                }
            }
        }
    }

    fun bookmarkCoin(data: Data) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!isBookmarked) {
                repository.upsert(data)
            } else {
                repository.delete(data)
            }
            isBookmarked = !isBookmarked
        }
    }
}