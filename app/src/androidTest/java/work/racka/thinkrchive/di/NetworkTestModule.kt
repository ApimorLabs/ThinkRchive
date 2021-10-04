package work.racka.thinkrchive.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import work.racka.thinkrchive.data.remote.api.ThinkrchiveApi
import work.racka.thinkrchive.data.local.database.ThinkpadDao
import work.racka.thinkrchive.data.remote.responses.ThinkpadResponse
import work.racka.thinkrchive.repository.ThinkpadRepository
import work.racka.thinkrchive.testUtils.Constants
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
@Module
object NetworkTestModule {

    @Singleton
    @Provides
    fun provideFakeThinkpadRepository(
        thinkrchiveApi: ThinkrchiveApi,
        thinkpadDao: ThinkpadDao
    ): ThinkpadRepository = ThinkpadRepository(thinkrchiveApi, thinkpadDao)

    @Singleton
    @Provides
    fun providesFakeThinkrchiveApi(): ThinkrchiveApi = object : ThinkrchiveApi {
        override suspend fun getThinkpads(): List<ThinkpadResponse> {
            return Constants.thinkpadResponseList
        }

    }
}