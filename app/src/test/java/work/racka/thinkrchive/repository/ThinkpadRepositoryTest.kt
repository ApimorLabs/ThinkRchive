package work.racka.thinkrchive.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.clearInvocations
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import work.racka.thinkrchive.data.api.ThinkrchiveApi
import work.racka.thinkrchive.data.dataTransferObjects.asDatabaseModel
import work.racka.thinkrchive.data.database.ThinkpadDao
import work.racka.thinkrchive.data.responses.ThinkpadResponse
import work.racka.thinkrchive.testUtils.FakeThinkpadData
import work.racka.thinkrchive.testUtils.MainCoroutineRule
import work.racka.thinkrchive.utils.Resource

@ExperimentalCoroutinesApi
class ThinkpadRepositoryTest {
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val api: ThinkrchiveApi = mock()
    private val dao: ThinkpadDao = mock()
    private lateinit var repo: ThinkpadRepository
    private val expectedList = FakeThinkpadData.fakeResponseList
        .asDatabaseModel()
        .toList()

    @Before
    fun setUp() {
        repo = ThinkpadRepository(api, dao, coroutineRule.dispatcher)
    }

    @After
    fun tearDown() {
        clearInvocations(api, dao)
    }

    @Test
    fun getAllThinkpadsFromNetwork_WhenResponseSuccess_ReturnsSuccessResource() {
        val expected = Resource.Success(
            data = FakeThinkpadData.fakeResponseList
        )
        coroutineRule.runBlockingTest {
            whenever(api.getThinkpads())
                .thenReturn(expected.data)
            val actual = repo.getAllThinkpadsFromNetwork()
            assertNotNull(actual.data)
            assertNull(actual.message)
            assertSame(expected.data, actual.data)
        }
    }

    @Test(expected = Exception::class)
    fun getAllThinkpadsFromNetwork_WhenResponseException_ReturnsErrorResource() {
        val expected = Resource.Error<List<ThinkpadResponse>>(
            message = "An error occurred: "
        )
        coroutineRule.runBlockingTest {
            whenever(api.getThinkpads())
                .thenThrow(Exception::class.java)
            val actual = repo.getAllThinkpadsFromNetwork()
            verify(api)
                .getThinkpads()
            assertNotNull(actual.message)
            assertNull(actual.data)
            assertEquals(expected.message, actual.message)
        }
    }

    @Test
    fun getAllThinkpads_WhenDbHasThinkpadList_ReturnsFlowOfThinkpadDbObjects() {
        val expected = flowOf(expectedList)
        whenever(dao.getAllThinkpads())
            .thenReturn(expected)
        val actual = repo.getAllThinkpads()
        verify(dao)
            .getAllThinkpads()
        assertEquals(expected, actual)
    }

    @Test
    fun queryThinkpads_WhenDbHasQuery_ReturnsFlowOfThinkpadDbObjects() {
        val query = ""
        val expected = flowOf(expectedList)
        whenever(dao.searchDatabase("%$query%"))
            .thenReturn(expected)
        val actual = repo.queryThinkpads(query)
        verify(dao)
            .searchDatabase("%$query%")
        assertEquals(expected, actual)
    }

    @Test
    fun getThinkpadsAlphaAscending_WhenDbHasQuery_ReturnsFlowOfThinkpadDbObjects() {
        val query = ""
        val expected = flowOf(expectedList)
        whenever(dao.getThinkpadsAlphaAscending("%$query%"))
            .thenReturn(expected)
        val actual = repo.getThinkpadsAlphaAscending(query)
        verify(dao)
            .getThinkpadsAlphaAscending("%$query%")
        assertEquals(expected, actual)
    }

    @Test
    fun getThinkpadsNewestFirst_WhenDbHasQuery_ReturnsFlowOfThinkpadDbObjects() {
        val query = ""
        val expected = flowOf(expectedList)
        whenever(dao.getThinkpadsNewestFirst("%$query%"))
            .thenReturn(expected)
        val actual = repo.getThinkpadsNewestFirst(query)
        verify(dao)
            .getThinkpadsNewestFirst("%$query%")
        assertEquals(expected, actual)
    }

    @Test
    fun getThinkpadsOldestFirst_WhenDbHasQuery_ReturnsFlowOfThinkpadDbObjects() {
        val query = ""
        val expected = flowOf(expectedList)
        whenever(dao.getThinkpadsOldestFirst("%$query%"))
            .thenReturn(expected)
        val actual = repo.getThinkpadsOldestFirst(query)
        verify(dao)
            .getThinkpadsOldestFirst("%$query%")
        assertEquals(expected, actual)
    }

    @Test
    fun getThinkpadsLowPriceFirst_WhenDbHasQuery_ReturnsFlowOfThinkpadDbObjects() {
        val query = ""
        val expected = flowOf(expectedList)
        whenever(dao.getThinkpadsLowPriceFirst("%$query%"))
            .thenReturn(expected)
        val actual = repo.getThinkpadsLowPriceFirst(query)
        verify(dao)
            .getThinkpadsLowPriceFirst("%$query%")
        assertEquals(expected, actual)
    }

    @Test
    fun getThinkpadsHighPriceFirst_WhenDbHasQuery_ReturnsFlowOfThinkpadDbObjects() {
        val query = ""
        val expected = flowOf(expectedList)
        whenever(dao.getThinkpadsHighPriceFirst("%$query%"))
            .thenReturn(expected)
        val actual = repo.getThinkpadsHighPriceFirst(query)
        verify(dao)
            .getThinkpadsHighPriceFirst("%$query%")
        assertEquals(expected, actual)
    }

    @Test
    fun refreshThinkpadList_WhenNewThinkpadRetrievedFromNetwork_InsertsThinkpadObjectsToDb() {
        coroutineRule.runBlockingTest {
            val expected = FakeThinkpadData
                .fakeResponseList
            whenever(
                dao.insertAllThinkpads(
                    *expected.asDatabaseModel()
                )
            ).thenReturn(Unit)
            repo.refreshThinkpadList(expected)
            verify(dao)
                .insertAllThinkpads(*expected.asDatabaseModel())
        }
    }

    /**
     * Exactly one ThinkpadDatabaseObject is returned because the exact model name
     * can only be one inside the database. Model is primary key in the DB
     */
    @Test
    fun getThinkpad_WhenExactModelNameProvided_ReturnsOneThinkpadObject() {
        val query = "Thinkpad T450"
        val expected = flowOf(expectedList.first())
        whenever(dao.getThinkpad(query))
            .thenReturn(expected)
        val actual = repo.getThinkpad(query)
        verify(dao)
            .getThinkpad(query)
        assertEquals(expected, actual)
    }
}