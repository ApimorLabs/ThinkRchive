package work.racka.thinkrchive.data.local.database

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.*
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
    fun readAndWriteToDb_ProvidedArrayOfThinkpad_ShouldReadAllThinkpadsInDb() {
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
    fun getThinkpadsAlphaAscending_ReturnsThinkpadsSortedByAlphabeticalAscendingOrderOfModelName() {
        val expected = TestData.ascendingOrderThinkpads.toList()
        val emptyQuery = "%%"
        val insertThinkpads = expected.shuffled().toTypedArray()
        coroutineRule.runBlocking {
            dao.insertAllThinkpads(*insertThinkpads)
            val data = dao.getThinkpadsAlphaAscending(emptyQuery)
            data.test {
                val actual = expectMostRecentItem()
                val checkModelAscending = actual.first().model < actual.last().model
                assertEquals(expected, actual)
                assertTrue(checkModelAscending)
            }
        }
    }

    @Test
    fun getThinkpadsNewestFirst_ReturnsThinkpadsSortedByNewestReleaseDateFirst() {
        val expected = TestData.thinkpadResponseList
            .asDatabaseModel()
            .toList()
            .reversed()
        val emptyQuery = "%%"
        val insertThinkpads = expected.shuffled().toTypedArray()
        coroutineRule.runBlocking {
            dao.insertAllThinkpads(*insertThinkpads)
            val data = dao.getThinkpadsNewestFirst(emptyQuery)
            data.test {
                val actual = expectMostRecentItem()
                val checkNewReleaseDate = actual.first().releaseDate > actual.last().releaseDate
                assertEquals(expected, actual)
                assertTrue(checkNewReleaseDate)
            }
        }
    }

    @Test
    fun getThinkpadsOldestFirst_ReturnsThinkpadsSortedByOldestReleaseDateFirst() {
        val expected = TestData.thinkpadResponseList
            .asDatabaseModel()
            .toList()
        val emptyQuery = "%%"
        val insertThinkpads = expected.shuffled().toTypedArray()
        coroutineRule.runBlocking {
            dao.insertAllThinkpads(*insertThinkpads)
            val data = dao.getThinkpadsOldestFirst(emptyQuery)
            data.test {
                val actual = expectMostRecentItem()
                val checkOldReleaseDate = actual.first().releaseDate < actual.last().releaseDate
                assertEquals(expected, actual)
                assertTrue(checkOldReleaseDate)
            }
        }
    }

    @Test
    fun getThinkpadsLowPriceFirst_ReturnsThinkpadsSortedByLowestPriceFirst() {
        val expected = TestData.thinkpadResponseList
            .asDatabaseModel()
            .toList()
        val emptyQuery = "%%"
        val insertThinkpads = expected.shuffled().toTypedArray()
        coroutineRule.runBlocking {
            dao.insertAllThinkpads(*insertThinkpads)
            val data = dao.getThinkpadsOldestFirst(emptyQuery)
            data.test {
                val actual = expectMostRecentItem()
                val checkLowPrice = actual.first().marketPriceStart < actual.last().marketPriceStart
                assertEquals(expected, actual)
                assertTrue(checkLowPrice)
            }
        }
    }

    @Test
    fun getThinkpadsHighPriceFirst_ReturnsThinkpadsSortedByHighestPriceFirst() {
        val expected = TestData.thinkpadResponseList
            .asDatabaseModel()
            .toList()
            .reversed()
        val emptyQuery = "%%"
        val insertThinkpads = expected.shuffled().toTypedArray()
        coroutineRule.runBlocking {
            dao.insertAllThinkpads(*insertThinkpads)
            val data = dao.getThinkpadsNewestFirst(emptyQuery)
            data.test {
                val actual = expectMostRecentItem()
                val checkHighPrice =
                    actual.first().marketPriceStart > actual.last().marketPriceStart
                assertEquals(expected, actual)
                assertTrue(checkHighPrice)
            }
        }
    }

    @Test
    fun getThinkpad_WhenThinkpadModelQueriedFound_ReturnsSingleThinkpadWithMatchingModel() {
        val expected = TestData.smallThinkpadDataBaseObjectsArray.first()
        val query = "Thinkpad T470"
        val insertThinkpads = TestData.smallThinkpadDataBaseObjectsArray
        coroutineRule.runBlocking {
            dao.insertAllThinkpads(*insertThinkpads)
            val data = dao.getThinkpad(query)
            data.test {
                val actual = expectMostRecentItem()
                val matchingModel = expected.model == actual.model
                assertEquals(expected, actual)
                assertTrue(matchingModel)
            }
        }
    }

    @Test
    fun getThinkpad_WhenThinkpadModelQueriedNotFound_ReturnsNull() {
        val query = "Dell XPS 9360"
        val insertThinkpads = TestData.smallThinkpadDataBaseObjectsArray
        coroutineRule.runBlocking {
            dao.insertAllThinkpads(*insertThinkpads)
            val data = dao.getThinkpad(query)
            data.test {
                val actual = expectMostRecentItem()
                assertNull(actual)
            }
        }
    }

}