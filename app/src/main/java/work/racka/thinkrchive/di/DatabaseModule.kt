package work.racka.thinkrchive.di

import android.content.Context
import androidx.room.Room
import com.github.theapache64.retrosheet.RetrosheetInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import work.racka.thinkrchive.data.api.ThinkrchiveApi
import work.racka.thinkrchive.data.database.ThinkpadDao
import work.racka.thinkrchive.data.database.ThinkpadDatabase
import work.racka.thinkrchive.repository.ThinkpadRepository
import work.racka.thinkrchive.utils.Constants.BASE_URL
import work.racka.thinkrchive.utils.Constants.THINKPAD_LIST_TABLE
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesThinkpadDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        ThinkpadDatabase::class.java,
        THINKPAD_LIST_TABLE
    ).fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun providesThinkpadDao(database: ThinkpadDatabase) = database.thinkpadDao
}