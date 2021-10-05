package work.racka.thinkrchive.data.dataTransferObjects


import org.junit.Assert.assertEquals
import org.junit.Test
import work.racka.thinkrchive.testUtils.FakeData

class DataTransferObjectsConvertersTest {

    @Test
    fun asDatabaseModel_ProvidedListOfThinkpadResponse_ReturnsArrayOfThinkpadDatabaseObjects() {
        val expected = FakeData.smallThinkpadDataBaseObjectsArray
        val responseList = FakeData.smallThinkpadResponseList
        val actual = responseList.asDatabaseModel()
        assertEquals(expected, actual)
    }

    @Test
    fun asDomainModel_ProvidedListOfThinkpadDatabaseObjects_ReturnsListOfThinkpad() {
        val expected = FakeData.smallThinkpadList
        val databaseObjectsList = FakeData.smallThinkpadDataBaseObjectsArray.toList()
        val actual = databaseObjectsList.asDomainModel()
        assertEquals(expected, actual)
    }

    @Test
    fun asThinkpad_ProvidedThinkpadDatabaseObject_ReturnsThinkpad() {
        val expected = FakeData.smallThinkpadList.first()
        val thinkpadDatabaseObject = FakeData.smallThinkpadDataBaseObjectsArray.first()
        val actual = thinkpadDatabaseObject.asThinkpad()
        assertEquals(expected, actual)
    }
}