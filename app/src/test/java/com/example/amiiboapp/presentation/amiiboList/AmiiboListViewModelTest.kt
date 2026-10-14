package com.example.amiiboapp.presentation.amiiboList

import app.cash.turbine.test
import com.example.amiiboapp.MainDispatcherRule
import com.example.amiiboapp.domain.model.Amiibo
import com.example.amiiboapp.domain.model.Release
import com.example.amiiboapp.domain.usecase.GetSortedAmiibosUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AmiiboListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var fakeRepository: FakeAmiiboRepository
    private lateinit var viewModel: AmiiboListViewModel

    private fun CreateViewModel(){
        viewModel = AmiiboListViewModel(GetSortedAmiibosUseCase(fakeRepository), fakeRepository)
    }

    @Before
    fun setUp(){
        fakeRepository = FakeAmiiboRepository()

        fakeRepository.setAmiibo(listOf(
            Amiibo("1", "1", "0", "BTesterAmiibo",
                "BTesterAmiiboCharacter", "testSeries", "testAmiiboSeries",
                "test", "test_image",
                Release(null, null, null, null), false),
            Amiibo("2", "2", "0", "ATesterAmiibo2",
                "ATesterAmiiboCharacter2", "testSeries2", "testAmiiboSeries2",
                "test2", "test_image2",
                Release(null, null, null, null), true),
        ))
        CreateViewModel()
    }

    @Test
    fun AfterLoadingThisPropertyHasFalse() = runTest {
        viewModel.uiState.test {
            val state = awaitItem()
            assertFalse(state.isLoading)
            assertEquals(2, state.amiibo.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun AmiibosDefaultSorted() = runTest {
        viewModel.uiState.test{
            val state = awaitItem()
            assertEquals("ATesterAmiibo2", state.amiibo.first().name)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun SelectFavoriteCliced() = runTest{
        viewModel.onFavoriteClicked("1")
        advanceUntilIdle()  // ждем завершения launch viewmodelscope

        viewModel.uiState.test {
            assertTrue(awaitItem().amiibo.first { it.id == "1" }.isFavorite)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun DeSelectFavoriteCliced() = runTest {
        viewModel.onFavoriteClicked("1")
        viewModel.onFavoriteClicked("1")

        advanceUntilIdle()  // ждем завершения launch viewmodelscope

        viewModel.uiState.test {
            assertFalse(awaitItem().amiibo.first { it.id == "1" }.isFavorite)
            cancelAndIgnoreRemainingEvents()
        }
    }

}