package work.racka.thinkrchive.utils

import org.junit.Assert.assertEquals
import org.junit.Test
import work.racka.thinkrchive.data.dataTransferObjects.asDatabaseModel
import work.racka.thinkrchive.data.dataTransferObjects.asDomainModel
import work.racka.thinkrchive.testUtils.FakeData

class UtilKtTest {

    @Test
    fun getChipNamesList_ProvidedListOfThinkpads_ReturnsSetOfModels() {
        val expected = listOf<String>("T Series", "X Series", "L Series", "P Series")
        val thinkpadList = FakeData.fakeResponseList
            .asDatabaseModel()
            .toList()
            .asDomainModel()
        val actual = thinkpadList.getChipNamesList()
        assertEquals(expected, actual)
    }
}