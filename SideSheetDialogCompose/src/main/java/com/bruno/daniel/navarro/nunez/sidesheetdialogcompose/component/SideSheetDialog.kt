package com.bruno.daniel.navarro.nunez.sidesheetdialogcompose.component

import androidx.compose.animation.*
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Create a [Duration] using a [Double]:
 *     val minutes = 10.0
 *     val minutes_to_millis = minutes.MIN
 *     // -- or --
 *     val minutes_to_millis = 10.0.MIN
 */
@Stable
inline val Double.MIN: Duration
    get() = Duration(millis = (this * 60000).toInt())

/**
 * Create a [Duration] using a [Double]:
 *     val seconds = 10.0
 *     val seconds_to_millis = minutes.SEG
 *     // -- or --
 *     val seconds_to_millis = 10.0.SEG
 */
@Stable
inline val Double.SEG: Duration
    get() = Duration(millis = (this * 1000).toInt())

/**
 * Create a [Duration] using a [Double]:
 *     val millis = 10.0
 *     val millis_to_millis = minutes.MILLIS
 *     // -- or --
 *     val millis_to_millis = 10.0.MILLIS
 */
@Stable
inline val Double.MILLIS: Duration
    get() = Duration(millis = this.toInt())

/**
 * Create a [Duration] using a [Int]:
 *     val minutes = 10
 *     val minutes_to_millis = minutes.MIN
 *     // -- or --
 *     val minutes_to_millis = 10.MIN
 */
@Stable
inline val Int.MIN: Duration
    get() = Duration(millis = (this * 60000))

/**
 * Create a [Duration] using a [Int]:
 *     val seconds = 10
 *     val seconds_to_millis = minutes.SEG
 *     // -- or --
 *     val seconds_to_millis = 10.SEG
 */
@Stable
inline val Int.SEG: Duration
    get() = Duration(millis = (this * 1000))

/**
 * Create a [Duration] using a [Int]:
 *     val millis = 10
 *     val millis_to_millis = minutes.MILLIS
 *     // -- or --
 *     val millis_to_millis = 10.MILLIS
 */
@Stable
inline val Int.MILIS: Duration
    get() = Duration(millis = this)

data class Duration(
    val millis: Int = 1000
)

data class SheetActions(
    val onClose: (key: Boolean) -> Unit,
    val callBackState: (key: Boolean) -> Unit
)

data class SheetAlignsParams(
    val sheetAlignment: Alignment = Alignment.CenterStart,
    val closeAlignment: Alignment.Horizontal = Alignment.End,
    val durationAnimation: Duration = Duration(500)
)

data class SheetIcon(
    val icon: ImageVector = Icons.Rounded.Close
)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SideSheet(
    modifier: Modifier = Modifier,
    sheetIcon: SheetIcon = SheetIcon(),
    sheetAction: SheetActions,
    sheetAlignsParams: SheetAlignsParams = SheetAlignsParams(),
    key: Boolean = false,
    contentView: (@Composable ColumnScope.() -> Unit)
) {
    var newKey by remember(key1 = key) { mutableStateOf(key) }
    val scope = rememberCoroutineScope()

    /**Personalizar transicion de animacion
     * - val hesitateEasing = CubicBezierEasing(0f, 1f, 1f, 0f)
     * */

    val durationShadowAnimation = sheetAlignsParams.durationAnimation.millis / 3
    AnimatedVisibility(
        visible = newKey,
        enter = getShadowEnterTransition(durationShadowAnimation),
        exit = getShadowExitTransition(durationShadowAnimation)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.Gray.copy(alpha = 0.5f))
                .clickable {
                    scope.launch {
                        newKey = false
                        delay(durationShadowAnimation.toLong() + 50)
                        sheetAction.onClose.invoke(newKey)
                    }
                }
        )
    }

    val initOffset = getInitOffset(sheetAlignsParams)

    val durationAnimation = sheetAlignsParams.durationAnimation.millis
    AnimatedVisibility(
        visible = newKey,
        enter = fadeIn(
            animationSpec = getAnimationSpec(
                durationAnimation = durationAnimation / 2
            )
        ) + slideInHorizontally(
            initialOffsetX = { initOffset },
            animationSpec = getAnimationSpec(
                durationAnimation = durationAnimation,
                delayMillis = durationAnimation / 2
            )
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { initOffset },
            animationSpec = getAnimationSpec(
                durationAnimation = durationAnimation
            )
        ) + fadeOut(
            animationSpec = getAnimationSpec(
                durationAnimation = durationAnimation / 2,
                delayMillis = durationAnimation
            )
        )
    ) {
        val cornerRadius = getCornerRadius(sheetAlignsParams)
        val initOffset2 = getInitOffset2(sheetAlignsParams)

        ///Derecha offset x = 60
        ///Izquierda offset x = 0 o -negativos
        Card(
            modifier = modifier
                .fillMaxWidth(0.85f)
                .fillMaxHeight()
                .offset(initOffset2.x.dp, 0.dp),
            elevation = 7.dp, shape = cornerRadius
        ) {
            Column(
                Modifier.padding(top = 5.dp, start = 5.dp, end = 5.dp, bottom = 20.dp)
            ) {
                IconButton(
                    modifier = Modifier
                        .size(40.dp)
                        .align(sheetAlignsParams.closeAlignment),
                    onClick = {
                        scope.launch {
                            newKey = false
                            delay(durationAnimation.toLong() + (durationAnimation / 2) + 50)
                            sheetAction.onClose.invoke(newKey)
                        }
                    }
                ) {
                    Icon(
                        sheetIcon.icon, contentDescription = "",
                        modifier = Modifier
                            .size(30.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }

                if (this@AnimatedVisibility.transition.currentState == this@AnimatedVisibility.transition.targetState) {
                    //Animation is completed when current state is the same as target state.
                    //you can call whatever you like here -> e.g. start music, show toasts, enable buttons, etc
                    sheetAction.callBackState.invoke(
                        newKey
                    ) //we invoke a callback as an example.
                }

                contentView.invoke(this)
            }
        }

    }
}

@OptIn(ExperimentalAnimationApi::class)
private fun getShadowExitTransition(
    durationShadowAnimation: Int
): ExitTransition {
    return fadeOut(
        animationSpec = getAnimationSpec(
            durationAnimation = durationShadowAnimation
        )
    )
}

@OptIn(ExperimentalAnimationApi::class)
private fun getShadowEnterTransition(
    durationShadowAnimation: Int
): EnterTransition {
    return fadeIn(
        animationSpec = getAnimationSpec(
            durationAnimation = durationShadowAnimation
        )
    )
}


private fun getInitOffset(sheetAlignsParams: SheetAlignsParams): Int {
    return when {
        isAlignEnd(sheetAlignsParams) -> {
            6000
        }
        isAlignStart(sheetAlignsParams) -> {
            -6000
        }
        else -> {
            6000
        }
    }
}

@Composable
private fun getInitOffset2(sheetAlignsParams: SheetAlignsParams): Offset {
    return when {
        isAlignEnd(sheetAlignsParams) -> {
            Offset(LocalConfiguration.current.screenWidthDp.toFloat() / 6, Offset.Zero.y)
        }
        isAlignStart(sheetAlignsParams) -> {
            Offset(Offset.Zero.x, Offset.Zero.y)
        }
        else -> {
            Offset(Offset.Zero.x, Offset.Zero.y)
        }
    }
}

private fun getCornerRadius(sheetAlignsParams: SheetAlignsParams): RoundedCornerShape {
    return when {
        isAlignEnd(sheetAlignsParams) -> {
            RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp)
        }
        isAlignStart(sheetAlignsParams) -> {
            RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp)
        }
        else -> {
            RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp)
        }
    }
}

private fun isAlignEnd(sheetAlignsParams: SheetAlignsParams): Boolean {
    return sheetAlignsParams.sheetAlignment == Alignment.CenterEnd
            || sheetAlignsParams.sheetAlignment == Alignment.BottomEnd
            || sheetAlignsParams.sheetAlignment == Alignment.TopEnd
            || sheetAlignsParams.sheetAlignment == Alignment.End
}

private fun isAlignStart(sheetAlignsParams: SheetAlignsParams): Boolean {
    return sheetAlignsParams.sheetAlignment == Alignment.CenterStart
            || sheetAlignsParams.sheetAlignment == Alignment.BottomStart
            || sheetAlignsParams.sheetAlignment == Alignment.TopStart
            || sheetAlignsParams.sheetAlignment == Alignment.Start
}

private fun <T> getAnimationSpec(
    durationAnimation: Int,
    delayMillis: Int = 0
): FiniteAnimationSpec<T> {
    return tween(
        durationMillis = durationAnimation,
        delayMillis = delayMillis,
        easing = LinearEasing // interpolator
    )
}