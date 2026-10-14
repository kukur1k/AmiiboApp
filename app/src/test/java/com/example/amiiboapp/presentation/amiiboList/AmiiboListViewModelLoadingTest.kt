package com.example.amiiboapp.presentation.amiiboList

import android.os.Debug
import android.os.Trace
import app.cash.turbine.test
import com.example.amiiboapp.MainDispatcherRule
import com.example.amiiboapp.domain.model.Amiibo
import com.example.amiiboapp.domain.model.Release
import com.example.amiiboapp.domain.usecase.GetSortedAmiibosUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class AmiiboListViewModelLoadingTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(StandardTestDispatcher())

    @Test
    fun isLoadingTrueBeforeAndAfter() = runTest{
        val fake = FakeAmiiboRepository().apply { setAmiibo(listOf(Amiibo("1", "1", "0", "TesterAmiibo",
            "TesterAmiiboCharacter", "testSeries", "testAmiiboSeries", "test", "test_image",
            Release(null, null, null, null), true))) }

        Debug.Tra("FAKE SIZE = ${fake.getAllAmiibo().size}")

        val viewModel = AmiiboListViewModel(GetSortedAmiibosUseCase(fake), fake)

        viewModel.uiState.test {
            assertTrue(awaitItem().isLoading)
            advanceUntilIdle()
            val loaded = awaitItem()
            Trace.("LOADED = $loaded")
            assertFalse(loaded.isLoading)
            assertEquals(1, loaded.amiibo.size)
            cancelAndIgnoreRemainingEvents()
        }
    }
}