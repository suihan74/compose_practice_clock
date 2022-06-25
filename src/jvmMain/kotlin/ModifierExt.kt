import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.awtEventOrNull
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import java.awt.event.MouseEvent

@Composable
fun Modifier.windowDraggable(windowProvider: ()-> WindowState) : Modifier =
    pointerInput(Unit) {
        forEachGesture {
            awaitPointerEventScope {
                val window = windowProvider()
                val firstEvent = awaitPointerEvent()
                if (MouseEvent.MOUSE_PRESSED != firstEvent.awtEventOrNull?.id) {
                    return@awaitPointerEventScope
                }
                val firstWindowPoint = firstEvent.awtEventOrNull?.locationOnScreen ?: return@awaitPointerEventScope
                val startPosition = window.position
                while (true) {
                    val event = awaitPointerEvent()
                    val currentPoint = event.awtEventOrNull?.locationOnScreen ?: continue
                    window.position = WindowPosition(
                        x = startPosition.x + (currentPoint.x - firstWindowPoint.x).dp,
                        y = startPosition.y + (currentPoint.y - firstWindowPoint.y).dp,
                    )
                    when (event.awtEventOrNull?.id) {
                        null, MouseEvent.MOUSE_RELEASED -> break
                    }
                }
            }
        }
    }