package work.racka.thinkrchive.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ThinkpadDatabaseObjects::class],
    version = 1
)
abstract class ThinkpadDatabase: RoomDatabase() {
    abstract val thinkpadDao: ThinkpadDao
}