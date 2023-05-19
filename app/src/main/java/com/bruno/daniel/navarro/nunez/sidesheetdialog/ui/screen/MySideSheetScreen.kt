package com.bruno.daniel.navarro.nunez.sidesheetdialog.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun MySideSheetScreen(
    modifier: Modifier = Modifier
){
    LazyColumn(modifier) {
        item {
            Text(
                text = "Hola Mundo Compose!!",
                fontSize = 24.sp,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold
            )
        }
    }
}