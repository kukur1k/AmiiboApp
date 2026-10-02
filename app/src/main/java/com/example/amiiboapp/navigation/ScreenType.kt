package com.example.amiiboapp.navigation

import android.icu.text.CaseMap
import com.example.amiiboapp.domain.model.Amiibo
import okhttp3.Route

sealed class ScreenType{
    object WithoutBN: ScreenType()
    object WithBN: ScreenType()
}

sealed class Screen(val route: String, val ScreenType: ScreenType){
    object AllAmiiboList: Screen(route = "allAmiiboList", ScreenType.WithBN)
    object NotesList: Screen(route = "notesList", ScreenType.WithBN)
    object NoteForm: Screen(route = "noteForm/{amiiboId}", ScreenType.WithoutBN){
        fun passId(amiiboId: String): String{
            return "noteForm/$amiiboId"
        }
    }
}

sealed class NavItem(val title: String, val route: String){
    object AllAmiiboList: NavItem("Amiibo", Screen.AllAmiiboList.route)
    object NotesList: NavItem("Заметки", Screen.NotesList.route)
}