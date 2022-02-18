package work.racka.thinkrchive.data.remote.api

import work.racka.thinkrchive.data.remote.responses.ThinkpadResponse

interface ThinkrchiveApi {

    suspend fun getThinkpads(): List<ThinkpadResponse>
}