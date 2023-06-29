# SideSheetComposeDialog

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![](https://jitpack.io/v/brunonavarro/SideSheetComposeDialog.svg)](https://jitpack.io/#brunonavarro/SideSheetComposeDialog)
![GitHub all releases](https://img.shields.io/github/downloads/brunonavarro/SideSheetComposeDialog/total)
![GitHub release (by tag)](https://img.shields.io/github/downloads/brunonavarro/SideSheetComposeDialog/1.0.0/total)


[![issues](https://img.shields.io/github/issues/brunonavarro/SideSheetComposeDialog?style=for-the-badge)](https://github.com/brunonavarro/SideSheetComposeDialog/issues)
[![pull requests](https://img.shields.io/github/issues-pr/brunonavarro/SideSheetComposeDialog?style=for-the-badge)](https://github.com/brunonavarro/SideSheetComposeDialog/pulls)
[![contributors](https://img.shields.io/github/contributors/brunonavarro/SideSheetComposeDialog?style=for-the-badge)](https://github.com/brunonavarro/SideSheetComposeDialog/graphs/contributors)

> #### Jetpack Compose is the modern Android toolkit for building native UIs. Simplifies and speeds up Android UI development. Bring your app to life quickly with less code, powerful tools, and intuitive Kotlin APIs.
> #### This is a Library project focused on Android Mobile solutions involving Open events in a highly customizable sidebar dialog. All developed with Jetpack Compose.
> — Bruno Navarro

##### With SideSheetComposeDialog, you can build applications that require adding additional content or actions in a sidesheet dialog, thus supporting tasks as part of a flow.
##### In this way you can take your projects to another level of design and user experience.


<img src="https://github.com/brunonavarro/SideSheetDialog/blob/main/SideSheetDialogCompose.jpeg" width="300" height="500" />


## See quickstart example: [SimpleExample](https://github.com/brunonavarro/SideSheetComposeDialog/blob/main/app/src/main/java/com/bruno/daniel/navarro/nunez/sidesheetdialog/MainActivity.kt)

### See example of open time in seconds: [Example 1](https://github.com/brunonavarro/SideSheetComposeDialog/blob/main/app/src/main/java/com/bruno/daniel/navarro/nunez/sidesheetdialog/MainActivity.kt)
### See example of opening time in Minutes: [Example 2](https://github.com/brunonavarro/SideSheetComposeDialog/blob/example2/app/src/main/java/com/bruno/daniel/navarro/nunez/sidesheetdialog/MainActivity.kt)
### See example of opening time in Milliseconds: [Example 3](https://github.com/brunonavarro/SideSheetComposeDialog/blob/example3/app/src/main/java/com/bruno/daniel/navarro/nunez/sidesheetdialog/MainActivity.kt)


## If you haven't started developing with Jetpack Compose yet, you can access the quickstart documentation here: [Jetpack Compose Quickstart](https://developer.android.com/jetpack/compose/setup?hl=es-419)


## Dependency Implementation
```gradle
/**Last Version*/
val version = '1.0.0'
```
```gradle
implementation 'com.github.brunonavarro:SideSheetComposeDialog:1.0.0'
```
Add in project build:
```gradle
repositories {
    ....
    maven{
        url 'https://jitpack.io'
    }
}
```

### Left Alignment XML Implementation
```xml
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    ...
    tools:context=".ui.secondXmlActivity.SecondXMLActivity">

    ....

    <!--Button to open the SideSheetDialog-->
    <Button
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginEnd="30dp"
        android:layout_marginBottom="30dp"
        android:text="Button"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent" />

    <!--ComposeView to enable compose on xml views
         -Relevant attributes:
         ---android:elevation="Xdp" //must be greater than 4dp
         ---android:visibility="gone"
         ---android:layout_width="wrap_content"
    -->
    <androidx.compose.ui.platform.ComposeView
        android:id="@+id/sideSheet"
        android:layout_width="wrap_content"
        android:layout_height="0dp"
        android:visibility="gone"
        android:elevation="7dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```
To add the Jetpack Compose compatible SideSheetDialog component
we must add the following code in the Activity/Fragment linked to the xml
Where the SideSheet will have an opening from Left to Right.

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    ....
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
```
For a Right to Left opening, you will have to align the ComposeView in the XML and add it in the Activity/Fragment
the next:
```kotlin
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
                    sheetAlignment = Alignment.BottomEnd,
                    closeAlignment = Alignment.Start
                )
            ) {
                /**My custom layout of the SideSheetDialog content */
                MySideSheetScreen()
            }
        }
    }
}
```
With all of this added, we now need to create a mutableStateOf variable initialized to either true or false, depending on the state
in which we want the SideSheetDialog to appear. Being true open and false closed.
```kotlin  
var isVisibleDrawer by mutableStateOf(false)
```
We also create the respective functions to make the change of state according to the clicks that are made

```kotlin
private fun closeOrShowDrawer(show: Boolean){
    isVisibleDrawer = show
    sheet.visibility = if (show) View.VISIBLE else View.GONE
}

private fun updateVisibleDrawer(show: Boolean){
    isVisibleDrawer = show
    sheet.visibility = if (show) View.VISIBLE else View.GONE
}
```

