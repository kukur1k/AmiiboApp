package com.example.amiiboapp.presentation.amiiboList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.amiiboapp.domain.model.Amiibo
import com.example.amiiboapp.domain.usecase.SortOrder
import com.example.amiiboapp.navigation.Screen
import coil3.compose.AsyncImage
import com.example.amiiboapp.R

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
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxWidth().clip(shape = RoundedCornerShape(20.dp))
            .background(Color(0xFFF6F6F8))
            .padding(5.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Сортировка", fontSize = 17.sp, textAlign = TextAlign.Center, fontWeight = FontWeight.SemiBold)
            Row() {
                SortOrder.entries.forEach { order ->
                    TextButton(onClick = { onSortChange(order)}) {
                        Text(text = order.name, fontSize = 11.sp)
                    }
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
            LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp, vertical = 40.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)) {
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
    Column(modifier = Modifier.fillMaxWidth()
        .shadow(10.dp, RectangleShape)
        .clip(shape = RoundedCornerShape(20.dp))
        .background(Color(0xFFF6F6F8))
        .padding(10.dp)

        , horizontalAlignment = Alignment.CenterHorizontally) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

            AsyncImage(
                model = amiibo.imageUrl,
                contentDescription = amiibo.name,
                modifier = Modifier.size(120.dp).fillMaxSize().clip(CircleShape),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.logo)
            )

            Column(modifier = Modifier.testTag("amiibo_item_${amiibo.id}")) {
                Text(amiibo.name)
                Text("Series - ${amiibo.amiiboSeries}")
                Row(modifier = Modifier.padding(10.dp)) {
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
        }


    }

}
