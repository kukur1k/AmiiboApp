package com.example.projectnavbottom.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amiiboapp.R
import com.example.amiiboapp.navigation.NavItem

class ArcTopShape(private val arcHeight: Float = 30f) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(0f, size.height)
            lineTo(0f, arcHeight)
            quadraticBezierTo(
                size.width / 2,  // контрольная точка по X (цен
                -arcHeight,      // контрольная точка по Y (поднимается выше)
                size.width,      // конечная X
                arcHeight        // конечная Y
            )
            lineTo(size.width, size.height)
            close()
        }
        return Outline.Generic(path)
    }
}


@Composable
fun BottomNavigationPanel(
    selectedItem: NavItem,
    onItemSelected: (NavItem) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .height(80.dp)
            .fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .align(Alignment.BottomCenter)
                .background(Color.Transparent),
            shape = ArcTopShape(arcHeight = 20f),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ItemNav(
                    item = NavItem.AllAmiiboList,
                    selected = selectedItem == NavItem.AllAmiiboList,
                    onClick = { onItemSelected(NavItem.AllAmiiboList) },
                    icon = painterResource(R.drawable.chess_svgrepo_com)
                )
                ItemNav(
                    item = NavItem.NotesList,
                    selected = selectedItem == NavItem.NotesList,
                    onClick = { onItemSelected(NavItem.NotesList) },
                    icon = painterResource(R.drawable.note_text_svgrepo_com)
                )

            }
        }

    }
}


@Composable
fun ItemNav(
    item: NavItem,
    selected: Boolean,
    onClick: () -> Unit,
    icon: Painter
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .width( 70.dp)
            .height( 70.dp)
    ) {
        Spacer(Modifier.height(3.dp))

        IconButton(
            onClick = { onClick() },
            modifier = Modifier
                .size(45.dp)
                .padding(top = 5.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.Transparent,
                contentColor = if (selected) Color(0xFF24B9EB) else Color(0xFF7C838C)
            )
        ) {
            Icon(
                painter = icon,
                contentDescription = item.title
            )
        }
        Text(
            text = item.title,
            fontSize = 10.sp,
            color = if (selected) Color(0xFF24B9EB) else Color(0xFF7C838C)
        )
    }
}