package work.racka.thinkrchive.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import work.racka.thinkrchive.data.database.ThinkpadDatabase
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