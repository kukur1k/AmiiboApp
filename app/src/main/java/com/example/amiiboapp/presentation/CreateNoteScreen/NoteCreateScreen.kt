package com.example.amiiboapp.presentation.CreateNoteScreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import coil3.compose.AsyncImage
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.amiiboapp.R

@Composable
fun NoteCreateScreen(
    viewModel: NoteCreateViewModel = hiltViewModel(),
    amiiboId: String,
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var pendingCameraUri by remember { mutableStateOf<Uri?>(null) }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            viewModel.onImageSelected(uri.toString(), amiiboId)
        }
    }

    val takePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        val uri = pendingCameraUri
        if (success && uri != null) {
            viewModel.onImageSelected(uri.toString(), amiiboId)
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            val uri = CreateCameraOutputUri(context)
            pendingCameraUri = uri
            takePhotoLauncher.launch(uri)
        }
    }

    Column(modifier = Modifier.padding(12.dp).fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = uiState.text,
            onValueChange = { viewModel.updateState(uiState.copy(text = it)) },
            label = { Text("текст") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.rating,
            onValueChange = { viewModel.updateState(uiState.copy(rating = it)) },
            label = { Text("Личный рейтинг") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        Row {
            Button(onClick = {
                pickImageLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }) {
                Text("Из галереи")
            }
            Spacer(Modifier.size(8.dp))
            Button(onClick = {
                cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
            }) {
                Text("С камеры")
            }
        }


        Spacer(Modifier.height(16.dp))
        Text(text = "Выбранное фото:",
            fontSize = 17.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold
        )
        AsyncImage(
            model = uiState.imagePath ?: R.drawable.logo,
            contentDescription = "фото",
            modifier = Modifier.size(56.dp),
            contentScale = ContentScale.Crop
        )



        Spacer(Modifier.height(16.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.insertNote(
                    amiiboId,
                    uiState.text,
                    uiState.rating.toInt(),
                    uiState.imagePath
                )
            },
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3361E0))
        ) {
            Text("Создать заметку")
        }

        Text(text = "${uiState.errors}", color = Color.Red)
    }
}
