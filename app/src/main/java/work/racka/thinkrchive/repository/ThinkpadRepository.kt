package work.racka.thinkrchive.repository

import dagger.hilt.android.scopes.ActivityScoped
import work.racka.thinkrchive.data.api.ThinkrchiveApi
import work.racka.thinkrchive.data.responses.ThinkpadResponse
import work.racka.thinkrchive.utils.Resource
import javax.inject.Inject

@ActivityScoped
class ThinkpadRepository @Inject constructor(
    private val thinkrchiveApi: ThinkrchiveApi
){

    // Get all the Thinkpads from the repository
    suspend fun getAllThinkpads(): Resource<List<ThinkpadResponse>> {
        val response = try {
            thinkrchiveApi.getThinkpads()
        } catch (e: Exception) {
            return Resource.Error(message = "An error occurred: ${e.message}")
        }
        return Resource.Success(data = response)
    }
}