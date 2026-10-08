package com.example.amiiboapp.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.amiiboapp.R
import com.example.amiiboapp.presentation.amiiboList.AmiiboListViewModel
import com.example.projectnavbottom.navigation.BottomNavigationPanel
@Composable
fun MainAppScaffold(navController: NavHostController
) {


    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry?.destination?.route


    val screenType = when {
        currentRoute?.startsWith("allAmiiboList") == true -> true
        currentRoute?.startsWith("notesList") == true -> true
        else -> {
            false
        }
    }

    var selectedItem by remember { mutableStateOf<NavItem>(NavItem.AllAmiiboList) }

    LaunchedEffect(currentRoute) {
        selectedItem = when {
            currentRoute?.startsWith("allAmiiboList") == true -> NavItem.AllAmiiboList
            currentRoute?.startsWith("notesList") == true -> NavItem.NotesList
            else -> {
                selectedItem
            }
        }

    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.img_1),
            contentDescription = "back",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                if(screenType) {
                    BottomNavigationPanel(
                        selectedItem = selectedItem,
                        onItemSelected = {
                            selectedItem = it
                            when (it) {
                                NavItem.AllAmiiboList -> {
                                    navController.navigate(Screen.AllAmiiboList.route) {
                                        popUpTo(Screen.AllAmiiboList.route) {
                                            inclusive = true
                                        }
                                    }
                                }

                                NavItem.NotesList -> {
                                    navController.navigate(Screen.NotesList.route) {
                                        popUpTo(Screen.NotesList.route) {
                                            inclusive = true
                                        }
                                    }
                                }


                            }
                        }

                    )
                }
            } ,
            contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)
        ) { paddingValues ->
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                AppNavigation(navController = navController)
            }
        }


    }


}


