package com.example.amiiboapp.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import com.example.amiiboapp.presentation.CreateNoteScreen.NoteCreateViewModel
import com.example.amiiboapp.presentation.amiiboList.AmiiboListViewModel
import com.example.amiiboapp.presentation.notes.NotesViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.amiiboapp.presentation.CreateNoteScreen.NoteCreateScreen
import com.example.amiiboapp.presentation.amiiboList.AmiiboListScreen
import com.example.amiiboapp.presentation.notes.NotesListScreen

@Composable
fun AppNavigation(navController: NavHostController,
){

    NavHost(navController = navController,
        startDestination = Screen.AllAmiiboList.route,

        ){
        composable(Screen.AllAmiiboList.route) {
            AmiiboListScreen(navController = navController,
                viewModel = hiltViewModel()
            )
        }

        composable(Screen.NotesList.route) {
            NotesListScreen(navController = navController,
                viewModel = hiltViewModel()
            )
        }

        composable(Screen.NoteForm.route,
            arguments = listOf(navArgument("amiiboId") {type = NavType.StringType})
        ) { backStackEntry ->
            val amiiboId = backStackEntry.arguments?.getString("amiiboId") ?: ""
            NoteCreateScreen(
                navController = navController,
                viewModel = hiltViewModel(),
                amiiboId = amiiboId
            )
        }

    }
}