package work.racka.thinkrchive.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ThinkpadDatabaseObject::class],
    version = 2
)
abstract class ThinkpadDatabase: RoomDatabase() {
    abstract val thinkpadDao: ThinkpadDao
}