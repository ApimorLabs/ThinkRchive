package work.racka.thinkrchive.data.remote.api

import com.github.theapache64.retrosheet.core.Read
import retrofit2.http.GET
import work.racka.thinkrchive.data.remote.responses.ThinkpadResponse

interface ThinkrchiveApi {
    @Read("SELECT *")
    @GET("all_laptops")
    suspend fun getThinkpads(): List<ThinkpadResponse>
}