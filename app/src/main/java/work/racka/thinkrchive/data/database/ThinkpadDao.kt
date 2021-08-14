package work.racka.thinkrchive.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import work.racka.thinkrchive.utils.Constants.THINKPAD_LIST_TABLE

@Dao
interface ThinkpadDao {

    // Get all Thinkpads stored in the database
    @Query("SELECT * FROM $THINKPAD_LIST_TABLE")
    fun getAllThinkpads(): Flow<List<ThinkpadDatabaseObjects>>

    @Query("SELECT * FROM $THINKPAD_LIST_TABLE WHERE model LIKE :query")
    fun searchDatabase(query: String): Flow<List<ThinkpadDatabaseObjects>>

    //Insert network data into the database upon update
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllThinkpads(vararg thinkpads: ThinkpadDatabaseObjects)
}