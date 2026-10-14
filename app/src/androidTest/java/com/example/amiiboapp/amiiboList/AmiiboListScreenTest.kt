package com.example.amiiboapp.amiiboList

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.amiiboapp.domain.model.Amiibo
import com.example.amiiboapp.domain.model.Release
import com.example.amiiboapp.domain.usecase.SortOrder
import com.example.amiiboapp.presentation.amiiboList.AmiiboListContent
import com.example.amiiboapp.presentation.amiiboList.AmiiboListUiState
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

class AmiiboListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val amiibo = Amiibo("1", "1", "0", "BTesterAmiibo",
        "BTesterAmiiboCharacter", "testSeries", "testAmiiboSeries",
        "test", "test_image",
        Release(null, null, null, null), false)

    private fun setContent(
        uiState: AmiiboListUiState = AmiiboListUiState(amiibo = listOf(amiibo), isLoading = false),
        onFavoriteClick: (String) -> Unit = {},
        onSortChange: (SortOrder) -> Unit = {},
    ){
        composeTestRule.setContent {
            val navController = rememberNavController()
            AmiiboListContent(
                uiState = uiState,
                onFavoriteClick = onFavoriteClick,
                onSortChange = onSortChange,
                navController = navController
            )
        }
    }

    @Test
    fun FavoriteClick(){
        var clickedId: String? = null
        setContent(onFavoriteClick = {clickedId = it})

        composeTestRule.onNodeWithTag("favorite_button_1").performClick()
        assertEquals("1", clickedId)
    }

    @Test
    fun ShowNameAmiibo() {
        setContent()
        composeTestRule.onNodeWithText("BTesterAmiibo").assertIsDisplayed()
    }

    @Test
    fun ShowSeriesAmiibo() {
        setContent()
        composeTestRule.onNodeWithText("Series - testSeries").assertIsDisplayed()
    }

    @Test
    fun ShowFullAmiiboItemCard() {
        setContent()
        composeTestRule.onNodeWithTag("amiibo_item_1").assertIsDisplayed()
    }

    @Test
    fun ShowLoadingSidebar() {
        setContent(AmiiboListUiState(amiibo = emptyList(), isLoading = true))
        composeTestRule.onNodeWithTag("loading").assertIsDisplayed()
    }

    @Test
    fun UnshowListWhenLoadingData() {
        setContent(AmiiboListUiState(amiibo = emptyList(), isLoading = true))
        composeTestRule.onNodeWithText("BTesterAmiibo").assertDoesNotExist()
    }

    @Test
    fun LoadingError() {
        setContent(
            AmiiboListUiState(
                amiibo = emptyList(),
                isLoading = false,
                errorMessage = "Не удалось загрузить Amiibo"
            )
        )
        composeTestRule.onNodeWithText("Не удалось загрузить Amiibo").assertIsDisplayed()
    }

    @Test
    fun SidebarNotShowWhenLoadingData() {
        setContent(
            AmiiboListUiState(
                amiibo = emptyList(),
                isLoading = true,
                errorMessage = "Не удалось загрузить Amiibo"
            )
        )
        composeTestRule.onNodeWithTag("loading").assertDoesNotExist()
    }

}