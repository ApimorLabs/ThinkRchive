package work.racka.thinkrchive.dataStore

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith

// Work in Progress
// Need to find the correct way to test DataStore
@RunWith(AndroidJUnit4::class)
class PrefDataStoreTest {
//    @get:Rule
//    val tempFolder = TemporaryFolder()
//
//    @ExperimentalCoroutinesApi
//    @get:Rule
//    val coroutineRule = MainCoroutineRule()
//
//    @get:Rule
//    val executorRule = InstantTaskExecutorRule()
//
//    private lateinit var repo: DataStoreRepository
//
//    @ExperimentalCoroutinesApi
//    @Before
//    fun setUp() {
//        val context = ApplicationProvider.getApplicationContext<Context>()
//        repo = DataStoreRepository(context, coroutineRule)
//    }
//
//    @After
//    fun tearDown() {
//
//    }
//
//    @ExperimentalTime
//    @ExperimentalCoroutinesApi
//    @Test
//    fun saveThemeSetting() {
//        val expected = 2
//        coroutineRule.runBlockingTest {
//            repo.saveThemeSetting(expected)
//            val flow = repo.readThemeSetting
//            flow.test {
//                val actual = expectMostRecentItem()
//                assertEquals(expected, actual)
//            }
//        }
//    }
//
//    @Test
//    fun getReadThemeSetting() {
//    }
//
//    @Test
//    fun saveSortOptionSetting() {
//    }
//
//    @Test
//    fun getReadSortOptionSetting() {
//    }
}