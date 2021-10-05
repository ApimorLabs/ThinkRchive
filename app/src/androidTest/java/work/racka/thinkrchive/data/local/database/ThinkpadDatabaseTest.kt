package work.racka.thinkrchive.data.local.database

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import work.racka.thinkrchive.data.dataTransferObjects.asDatabaseModel
import work.racka.thinkrchive.testUtils.MainCoroutineRuleAndroidTest
import work.racka.thinkrchive.testUtils.TestData
import kotlin.time.ExperimentalTime

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
@ExperimentalTime
class ThinkpadDatabaseTest {

    @get:Rule
    val coroutineRule = MainCoroutineRuleAndroidTest()

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val thinkpadArray = TestData.smallThinkpadDataBaseObjectsArray

    private lateinit var dao: ThinkpadDao
    private lateinit var db: ThinkpadDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            ThinkpadDatabase::class.java
        ).allowMainThreadQueries()
            .build()
        dao = db.thinkpadDao
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertAllThinkpads_ProvidedArrayOfThinkpad_ShouldHaveThinkpadsInDb() {
        val expected = thinkpadArray.toList()
        coroutineRule.runBlocking {
            dao.insertAllThinkpads(*thinkpadArray)
            val data = dao.getAllThinkpads()
            data.test {
                val actual = expectMostRecentItem()
                assertEquals(expected, actual)
            }
        }
    }

    @Test
    fun searchDatabase_WhenQueryMatchesThinkpadModelName_ReturnsThinkpadListContainingModel() {
        val thinkpadList = TestData.thinkpadResponseList.asDatabaseModel()
        val expected = thinkpadList.last()
        val searchQuery = "%T470%"
        coroutineRule.runBlocking {
            dao.insertAllThinkpads(*thinkpadList)
            val data = dao.searchDatabase(searchQuery)
            data.test {
                val actual = expectMostRecentItem()
                assertTrue(actual.contains(expected))
                assertEquals(expected, actual.first())
            }
        }
    }

    @Test
    fun searchDatabase_WhenQueryDoesNotMatchThinkpadModelName_ReturnsEmptyList() {
        val thinkpadList = TestData.thinkpadResponseList.asDatabaseModel()
        val expected = listOf<ThinkpadDatabaseObject>()
        val searchQuery = "%Dell XPS%"
        coroutineRule.runBlocking {
            dao.insertAllThinkpads(*thinkpadList)
            val data = dao.searchDatabase(searchQuery)
            data.test {
                val actual = expectMostRecentItem()
                assertEquals(expected, actual)
            }
        }
    }

    @Test
    fun getAllThinkpadsAlphaAscending_ReturnsThinkpadsSortedByAlphabeticalAscendingOrderOfModelName() {

    }

}