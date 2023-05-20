# SideSheetComposeDialog
 
![Descripción de la imagen](https://github.com/brunonavarro/SideSheetDialog/blob/main/SideSheetDialogCompose.jpeg)

 
## Implementacion de Dependencia
```gradle
implementation 'com.github.brunonavarro:SideSheetComposeDialog:v1.0.0_Beta-01'
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

