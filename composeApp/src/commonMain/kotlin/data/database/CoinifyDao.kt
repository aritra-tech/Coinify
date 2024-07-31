package data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import domain.model.Data
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinifyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(data: Data)

    @Delete
    suspend fun delete(data: Data)

    @Query("DELETE FROM data")
    suspend fun deleteAllBookmark()

    @Query("SELECT * FROM data")
    fun getCoins(): List<Data>

    @Query("SELECT * FROM data WHERE id = :id")
    suspend fun getCoin(id: Int): Data?
}