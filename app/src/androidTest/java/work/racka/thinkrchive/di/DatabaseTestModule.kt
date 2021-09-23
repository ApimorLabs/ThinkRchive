package work.racka.thinkrchive.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import work.racka.thinkrchive.data.database.ThinkpadDao
import work.racka.thinkrchive.data.database.ThinkpadDatabase
import work.racka.thinkrchive.utils.Constants
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
@Module
object DatabaseTestModule {

    @Singleton
    @Provides
    fun providesFakeDb(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        ThinkpadDatabase::class.java,
        Constants.THINKPAD_LIST_TABLE
    ).fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()

    @Singleton
    @Provides
    fun providesFakeDao(db: ThinkpadDatabase): ThinkpadDao = db.thinkpadDao
}