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
import work.racka.thinkrchive.data.database.ThinkpadDatabase
import work.racka.thinkrchive.repository.ThinkpadRepository
import work.racka.thinkrchive.utils.Constants.BASE_URL
import work.racka.thinkrchive.utils.Constants.THINKPAD_LIST_TABLE
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    /**
     * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
     * full Kotlin compatibility.
     */
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    // Retrosheet interceptor to write and read data from Google Sheets
    private val retrosheetInterceptor = RetrosheetInterceptor.Builder()
        .setLogging(false)
        .addSheet(
            sheetName = "all_laptops",
            columns = arrayOf(
                "model", "image_url", "release_date", "series",	"market_price_start", "market_price_end",
                "processor_platforms", "processors",	"graphics",	"max_ram", "display_res",
                "touch_screen",	"screen_size", "backlit_kb", "fingerprint_reader", "kb_type",
                "dual_batt", "internal_batt", "external_batt", "psref_link", "bios_version",
                "known_issues", "known_issues_links", "displays_supported",	"other_mods",
                "other_mods_links",	"bios_lock_in"
            )
        )
        .build()

    // Build okHttpClient with the RetrofitInterceptor
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(retrosheetInterceptor)
        .build()

    @Singleton
    @Provides
    fun providesThinkpadRepository( thinkrchiveApi: ThinkrchiveApi) = ThinkpadRepository(thinkrchiveApi)

    @Singleton
    @Provides
    fun providesThinkrchiveApi(): ThinkrchiveApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ThinkrchiveApi::class.java)

    @Singleton
    @Provides
    fun providesThinkpadDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        ThinkpadDatabase::class.java,
        THINKPAD_LIST_TABLE
    ).build()

    @Singleton
    @Provides
    fun providesThinkpadDao(database: ThinkpadDatabase) = database.thinkpadDao
}