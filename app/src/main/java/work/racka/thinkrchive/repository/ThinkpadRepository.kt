package work.racka.thinkrchive.repository

import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import work.racka.thinkrchive.data.api.ThinkrchiveApi
import work.racka.thinkrchive.data.dataTransferObjects.asDatabaseModel
import work.racka.thinkrchive.data.database.ThinkpadDao
import work.racka.thinkrchive.data.database.ThinkpadDatabaseObject
import work.racka.thinkrchive.data.responses.ThinkpadResponse
import work.racka.thinkrchive.utils.Resource
import javax.inject.Inject

@ActivityScoped
class ThinkpadRepository @Inject constructor(
    private val thinkrchiveApi: ThinkrchiveApi,
    private val thinkpadDao: ThinkpadDao
) {

    // Get all the Thinkpads from the network
    suspend fun getAllThinkpadsFromNetwork(): Resource<List<ThinkpadResponse>> {
        val response = try {
            thinkrchiveApi.getThinkpads()
        } catch (e: Exception) {
            return Resource.Error(message = "An error occurred: ${e.message}")
        }
        return Resource.Success(data = response)
    }

    // Get all Thinkpads from the Database
    fun getAllThinkpads(): Flow<List<ThinkpadDatabaseObject>> {
        return thinkpadDao.getAllThinkpads()
    }

    // Get requested Thinkpads from the Database
    // This is the default way of getting the data from the database
    // Depends on Sorting options
    fun queryThinkpads(query: String): Flow<List<ThinkpadDatabaseObject>> {
        return thinkpadDao.searchDatabase("%$query%")
    }

    fun getThinkpadsAlphaAscending(query: String): Flow<List<ThinkpadDatabaseObject>> {
        return thinkpadDao.getThinkpadsAlphaAscending("%$query%")
    }

    fun getThinkpadsNewestFirst(query: String): Flow<List<ThinkpadDatabaseObject>> {
        return thinkpadDao.getThinkpadsNewestFirst("%$query%")
    }

    fun getThinkpadsOldestFirst(query: String): Flow<List<ThinkpadDatabaseObject>> {
        return thinkpadDao.getThinkpadsOldestFirst("%$query%")
    }

    fun getThinkpadsLowPriceFirst(query: String): Flow<List<ThinkpadDatabaseObject>> {
        return thinkpadDao.getThinkpadsLowPriceFirst("%$query%")
    }

    fun getThinkpadsHighPriceFirst(query: String): Flow<List<ThinkpadDatabaseObject>> {
        return thinkpadDao.getThinkpadsHighPriceFirst("%$query%")
    }

    // Insert all Thinkpads obtained from the network to the database
    suspend fun refreshThinkpadList(thinkpadList: List<ThinkpadResponse>) {
        withContext(Dispatchers.IO) {
            thinkpadDao.insertAllThinkpads(*thinkpadList.asDatabaseModel())
        }
    }

    // Get a single Thinkpad entry from the DB
    fun getThinkpad(thinkpad: String): Flow<ThinkpadDatabaseObject> {
        return thinkpadDao.getThinkpad(thinkpad = thinkpad)
    }
}