package com.bruno.daniel.navarro.nunez.sidesheetdialog

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
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
import com.bruno.daniel.navarro.nunez.sidesheetdialog.ui.secondXmlActivity.SecondXMLActivity
import com.bruno.daniel.navarro.nunez.sidesheetdialog.ui.theme.SideSheetDialogTheme
import com.bruno.daniel.navarro.nunez.sidesheetdialog.ui.threeXmlActivity.ThreeXMLActivity
import com.bruno.daniel.navarro.nunez.sidesheetdialogcompose.component.*

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
                    isVisibleDrawer = isVisibleDrawer,
                    openActivity = ::openSecondActivity
                )
            }
        }
    }

    private fun openSecondActivity(id: Int) {
        val intent = Intent(
            this,
            when (id) {
                0 -> {
                    SecondXMLActivity::class.java
                }
                1 -> {
                    ThreeXMLActivity::class.java
                }
                else -> {
                    SecondXMLActivity::class.java
                }
            }
        )
        startActivity(intent)
    }

    private fun closeOrShowDrawer(show: Boolean) {
        isVisibleDrawer = show
    }

    private fun updateVisibleDrawer(show: Boolean) {
        isVisibleDrawer = show
    }
}


@Composable
fun Greeting(
    updateVisibleDrawer: (visible: Boolean) -> Unit = {},
    closeOrShowDrawer: (visible: Boolean) -> Unit = {},
    isVisibleDrawer: Boolean = true,
    openActivity: (id: Int) -> Unit = {}
) {

    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
    ) {

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(start = 30.dp, end = 30.dp)
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = true,
                onClick = {
                    openActivity.invoke(1)
                }
            ) {
                Text(text = "Open Activity XML Rigth", color = Color.White)
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = true,
                onClick = {
                    openActivity.invoke(2)
                }
            ) {
                Text(text = "Open Activity XML Left", color = Color.White)
            }
        }

        IconButton(
            onClick = {
                updateVisibleDrawer(true)
            },
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.BottomEnd)
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
            closeAlignment = Alignment.End,
            durationAnimation = 0.2.MILLIS
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