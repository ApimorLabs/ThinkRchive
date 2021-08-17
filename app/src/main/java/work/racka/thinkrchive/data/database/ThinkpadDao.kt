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
    fun getAllThinkpads(): Flow<List<ThinkpadDatabaseObject>>

    @Query("SELECT * FROM $THINKPAD_LIST_TABLE WHERE model LIKE :query")
    fun searchDatabase(query: String): Flow<List<ThinkpadDatabaseObject>>

    // Insert network data into the database upon update
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllThinkpads(vararg thinkpads: ThinkpadDatabaseObject)

    // Get a Thinkpad entry from it's name
    // We are searching the primary key so it should always match
    // only one Thinkpad model
    @Query("SELECT * FROM $THINKPAD_LIST_TABLE WHERE model LIKE :thinkpad")
    fun getThinkpad(thinkpad: String): Flow<ThinkpadDatabaseObject>
}