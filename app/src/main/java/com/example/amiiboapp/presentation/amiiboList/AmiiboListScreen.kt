package com.example.amiiboapp.presentation.amiiboList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.amiiboapp.domain.model.Amiibo
import com.example.amiiboapp.domain.usecase.SortOrder
import com.example.amiiboapp.navigation.Screen

@Composable
fun AmiiboListScreen(viewModel: AmiiboListViewModel = hiltViewModel(),
                     navController: NavHostController) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AmiiboListContent(
        uiState = uiState,
        onFavoriteClick = viewModel::onFavoriteClicked,
        onSortChange = viewModel::onSortOrderChanged,
        navController = navController
    )
}

@Composable
fun AmiiboListContent(
    navController: NavHostController,
    uiState: AmiiboListUiState,
    onFavoriteClick: (String) -> Unit,
    onSortChange: (SortOrder) -> Unit
) {
    Column() {
        Row {
            SortOrder.entries.forEach { order ->
                TextButton(onClick = { onSortChange(order)}) {
                    Text(order.name)
                }
            }
        }

        if (uiState.errorMessage != null){
            Text(uiState.errorMessage, color = MaterialTheme.colorScheme.error)
        }
        else if (uiState.isLoading){
            CircularProgressIndicator(modifier = Modifier.testTag("loading"))
        }
        else{
            LazyColumn {
                items(uiState.amiibo, key = {it.id}){ amiibo ->
                    AmiiboItem(
                        amiibo = amiibo,
                        onFavoriteClick = {onFavoriteClick(amiibo.id)},
                        onAddNoteClick = {navController.navigate(Screen.NoteForm.passId(amiibo.id))}
                    )
                }
            }
        }

    }
}


@Composable
fun AmiiboItem(amiibo: Amiibo, onFavoriteClick: () -> Unit, onAddNoteClick: () -> Unit) {
    Row(modifier = Modifier.testTag("amiibo_item_${amiibo.id}")) {
        Text(amiibo.name, modifier = Modifier.weight(1f))
        Text("Series - ${amiibo.amiiboSeries}")
        IconButton(
            onClick = onFavoriteClick,
            modifier = Modifier.testTag("favorite_button_${amiibo.id}")
        ) {
            Icon(
                imageVector = if (amiibo.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Избранное"
            )
        }
        IconButton(
            onClick = onAddNoteClick,
            modifier = Modifier.testTag("addNote_button_${amiibo.id}")
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Слздать заметку"
            )
        }
    }
}