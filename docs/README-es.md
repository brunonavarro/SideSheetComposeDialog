# SideSheetComposeDialog

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![](https://jitpack.io/v/brunonavarro/SideSheetComposeDialog.svg)](https://jitpack.io/#brunonavarro/SideSheetComposeDialog)
![GitHub all releases](https://img.shields.io/github/downloads/brunonavarro/SideSheetComposeDialog/total)
![GitHub release (by tag)](https://img.shields.io/github/downloads/brunonavarro/SideSheetComposeDialog/1.0.0/total)


[![issues](https://img.shields.io/github/issues/brunonavarro/SideSheetComposeDialog?style=for-the-badge)](https://github.com/brunonavarro/SideSheetComposeDialog/issues)
[![pull requests](https://img.shields.io/github/issues-pr/brunonavarro/SideSheetComposeDialog?style=for-the-badge)](https://github.com/brunonavarro/SideSheetComposeDialog/pulls)
[![contributors](https://img.shields.io/github/contributors/brunonavarro/SideSheetComposeDialog?style=for-the-badge)](https://github.com/brunonavarro/SideSheetComposeDialog/graphs/contributors)


> #### Jetpack Compose es el kit de herramientas moderno de Android para compilar IU nativas. Simplifica y acelera el desarrollo de la IU en Android. Haz que tu app cobre vida rápidamente con menos código, herramientas potentes y APIs intuitivas de Kotlin.
> #### Este es un proyecto de Libreria enfocado a soluciones Mobile Android que involucren eventos de Abrir una barra de dialogo lateral altamente personalizable. Todo desarrollado con Jetpack Compose.
>   — Bruno Navarro

##### Con SideSheetComposeDialog, puedes construir aplicaciones que requieran agregar contenido o acciones adicionales en un cuadro de dialogo lateral, y asi apoyar tareas como parte de un flujo.
##### De esta forma podras llevar tus proyectos a otro nivel de diseño y experiencia de usuario.


<img src="https://github.com/brunonavarro/SideSheetDialog/blob/main/SideSheetDialogCompose.jpeg" width="300" height="500" />


## Ver ejemplo de inicio rapido: [SimpleExample](https://github.com/brunonavarro/SideSheetComposeDialog/blob/main/app/src/main/java/com/bruno/daniel/navarro/nunez/sidesheetdialog/MainActivity.kt)

### Ver ejemplo de tiempo de apertura en segundos: [Ejemplo 1](https://github.com/brunonavarro/SideSheetComposeDialog/blob/main/app/src/main/java/com/bruno/daniel/navarro/nunez/sidesheetdialog/MainActivity.kt)
### Ver ejemplo de tiempo de apertura en Minutos: [Ejemplo 2](https://github.com/brunonavarro/SideSheetComposeDialog/blob/ejemplo2/app/src/main/java/com/bruno/daniel/navarro/nunez/sidesheetdialog/MainActivity.kt)
### Ver ejemplo de tiempo de apertura en Milisegundos: [Ejemplo 3](https://github.com/brunonavarro/SideSheetComposeDialog/blob/ejemplo3/app/src/main/java/com/bruno/daniel/navarro/nunez/sidesheetdialog/MainActivity.kt)

## Si aun no has iniciado en el desarrollo con Jetpack Compose puedes acceder a la documentación de inicio rapido aquí: [Inicio Rapido Jetpack Compose](https://developer.android.com/jetpack/compose/setup?hl=es-419)


## Implementacion de Dependencia
```gradle
/**Last Version*/
val version = '1.0.0'
```
```gradle
implementation 'com.github.brunonavarro:SideSheetComposeDialog:1.0.0'
```
Agrega en build de proyecto:
```gradle
repositories {
    ....
    maven{
        url 'https://jitpack.io'
    }
}
```

### Implementacion XML alineacion Izquierda 
```xml
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    ...
    tools:context=".ui.secondXmlActivity.SecondXMLActivity">

    ....

    <!--Boton para abrir el SideSheetDialog-->
    <Button
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginEnd="30dp"
        android:layout_marginBottom="30dp"
        android:text="Button"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent" />

    <!--ComposeView para habilitar compose en vistas xml
          -Atributos relevantes:
            ---android:elevation="Xdp" //debe ser superior a 4dp
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
Para añadir el componente SideSheetDialog de compatible con Jetpack Compose
debemos agregar el siguiente codigo en el Activity/Fragment enlazado al xml
Donde el SideSheet tendra una apertura de Izquierda a Derecha.

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
Para una apertura de Derecha a Izquierda habrá que alinear el ComposeView en el XML y agregar en el Activity/Fragment
lo siguiente:
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
                /**Mi diseño personalizado del contenido del SideSheetDialog */
                MySideSheetScreen()
            }
        }
    }
}
```
Con todo esto agregado, ahora debemos crear una variable mutableStateOf inicializada como true o false, dependiendo del estado 
en el que queremos que aparezca el SideSheetDialog. Siendo true abierto y false cerrado.

```kotlin  
var isVisibleDrawer by mutableStateOf(false)
```
Creamos tambien las respectivas funciones para realizar el cambio de estado acorde a los clicks que se realicen

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

