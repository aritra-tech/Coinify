package data.repository

import data.database.CoinifyDao
import domain.model.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.flowOn

class CoinifyRepository(
    private val dao: CoinifyDao
) {

    suspend fun upsert(data: Data) {
        dao.upsert(data)
    }

    suspend fun delete(data: Data) {
        dao.delete(data)
    }

    suspend fun deleteAllBookmark() {
        dao.deleteAllBookmark()
    }

    fun getCoins() {
        dao.getCoins()
    }

    suspend fun getCoin(coinId: Int): Data? {
        return dao.getCoin(id = coinId)
    }
}