package work.racka.thinkrchive.repository

import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import work.racka.thinkrchive.data.api.ThinkrchiveApi
import work.racka.thinkrchive.data.dataTransferObjects.asDatabaseModel
import work.racka.thinkrchive.data.database.ThinkpadDao
import work.racka.thinkrchive.data.database.ThinkpadDatabaseObjects
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
    fun getAllThinkpads(): Flow<List<ThinkpadDatabaseObjects>> {
        return thinkpadDao.getAllThinkpads()
    }

    // Insert all Thinkpads obtained from the network to the database
    suspend fun refreshThinkpadList(thinkpadList: List<ThinkpadResponse>) {
        withContext(Dispatchers.IO) {
            thinkpadDao.insertAllThinkpads(*thinkpadList.asDatabaseModel())
        }
    }
}