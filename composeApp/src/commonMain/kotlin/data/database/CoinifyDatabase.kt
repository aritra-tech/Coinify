package data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import domain.model.Data

@Database(entities = [Data::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class CoinifyDatabase: RoomDatabase() {
    abstract fun coinifyDao(): CoinifyDao
}