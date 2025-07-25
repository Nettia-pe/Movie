package pe.nettia.movie.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import pe.nettia.movie.data.local.dao.MovieDao
import pe.nettia.movie.data.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
} 