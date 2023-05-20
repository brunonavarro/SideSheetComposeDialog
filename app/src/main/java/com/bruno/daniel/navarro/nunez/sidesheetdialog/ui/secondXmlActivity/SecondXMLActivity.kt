package com.bruno.daniel.navarro.nunez.sidesheetdialog.ui.secondXmlActivity

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.bruno.daniel.navarro.nunez.sidesheetdialog.R
import com.bruno.daniel.navarro.nunez.sidesheetdialog.ui.screen.MySideSheetScreen
import com.bruno.daniel.navarro.nunez.sidesheetdialog.ui.theme.SideSheetDialogTheme
import com.bruno.daniel.navarro.nunez.sidesheetdialogcompose.component.SheetActions
import com.bruno.daniel.navarro.nunez.sidesheetdialogcompose.component.SheetAlignsParams
import com.bruno.daniel.navarro.nunez.sidesheetdialogcompose.component.SideSheet

class SecondXMLActivity : AppCompatActivity() {

    var isVisibleDrawer by mutableStateOf(false)

    lateinit var sheet: ComposeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_xmlactivity)
        sheet = findViewById(R.id.sideSheet)
        val boton = findViewById<Button>(R.id.button)

        boton.setOnClickListener {
            updateVisibleDrawer(true)
        }

        sheet.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                SideSheetDialogTheme {
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

            }
        }
    }

    private fun closeOrShowDrawer(show: Boolean) {
        isVisibleDrawer = show
        sheet.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun updateVisibleDrawer(show: Boolean) {
        isVisibleDrawer = show
        sheet.visibility = if (show) View.VISIBLE else View.GONE
    }
}