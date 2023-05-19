package com.bruno.daniel.navarro.nunez.sidesheetdialog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bruno.daniel.navarro.nunez.sidesheetdialog.ui.screen.MySideSheetScreen
import com.bruno.daniel.navarro.nunez.sidesheetdialog.ui.theme.SideSheetDialogTheme
import com.bruno.daniel.navarro.nunez.sidesheetdialogcompose.component.SheetActions
import com.bruno.daniel.navarro.nunez.sidesheetdialogcompose.component.SheetAlignsParams
import com.bruno.daniel.navarro.nunez.sidesheetdialogcompose.component.SideSheet

class MainActivity : ComponentActivity() {

    var isVisibleDrawer by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SideSheetDialogTheme {
                // A surface container using the 'background' color from the theme
                Greeting(
                    updateVisibleDrawer = ::updateVisibleDrawer,
                    closeOrShowDrawer = ::closeOrShowDrawer,
                    isVisibleDrawer = isVisibleDrawer
                )

            }
        }
    }

    private fun closeOrShowDrawer(show: Boolean){
        isVisibleDrawer = show
    }

    private fun updateVisibleDrawer(show: Boolean){
        isVisibleDrawer = show
    }
}



@Composable
fun Greeting(
    updateVisibleDrawer: (visible: Boolean) -> Unit = {

    },
    closeOrShowDrawer: (visible: Boolean) -> Unit = {

    },
    isVisibleDrawer: Boolean = true
) {

    Box(
        Modifier.fillMaxWidth().fillMaxHeight().background(Color.White)
    ) {
        IconButton(
            onClick = {
                updateVisibleDrawer(true)
            },
            modifier = Modifier.size(80.dp).align(Alignment.BottomEnd)
        ) {
            Icon(Icons.Rounded.Info, contentDescription = "", modifier = Modifier.size(50.dp))
        }
    }

    SideSheet(
        key = isVisibleDrawer,
        sheetAction = SheetActions(
            onClose = {
                closeOrShowDrawer(it)
            },
            callBackState = {
                updateVisibleDrawer(it)
            }
        ),
        sheetAlignsParams = SheetAlignsParams(
            sheetAlignment = Alignment.BottomStart,
            closeAlignment = Alignment.End
        )
    ) {
        MySideSheetScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SideSheetDialogTheme {
        Greeting()
    }
}