import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.awt.image.BufferedImage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun main() {
    application {
        val windowState = rememberWindowState(width = 380.dp, height = 180.dp)
        Window(
            onCloseRequest = ::exitApplication,
            visible = true,
            title = "",
            state = windowState,
            icon = BitmapPainter(BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).toComposeImageBitmap()),
            alwaysOnTop = true,
            resizable = false,
            transparent = true,
            undecorated = true
        ) {
            val currentTimeText = remember { mutableStateOf("") }
            val todayText = remember { mutableStateOf("") }

            val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
            val dateFormatter = DateTimeFormatter.ofPattern("uuuu年MM月dd日(E)")

            // 時刻更新
            GlobalScope.launch(Dispatchers.Main) {
                while (true) {
                    val now = LocalDateTime.now()
                    currentTimeText.value = now.format(timeFormatter)
                    todayText.value = now.format(dateFormatter)

                    delay(
                        (1_000_000_000L - now.nano) / 1_000_000L
                    )
                }
            }

            // 画面レイアウト
            MaterialTheme {
                MainContent(windowState, currentTimeText, todayText)
            }
        }
    }
}

@Composable
fun MainContent(windowState: WindowState?, currentTimeText: State<String>, todayText: State<String>) {
    val modifier = Modifier
        .fillMaxSize()
        .let { modifier ->
            windowState?.let {
                modifier.windowDraggable { windowState }
            } ?: modifier
        }

    val white = Color(0xfff6f6f6)
    val black = Color(0xff060606)
    val textColor = remember { mutableStateOf(white) }
    val blurColor = remember { mutableStateOf(black) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy((-10).dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DisableSelection {
            BlurText(
                color = textColor.value,
                blurColor = blurColor.value,
                blurRadius = 8.dp,
                fontSize = 72.sp,
                fontFamily = Fonts.migmix,
                textAlign = TextAlign.Center,
                text = currentTimeText.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        detectTapGestures(onDoubleTap = {
                            textColor.value =
                                if (textColor.value == white) black
                                else white
                            blurColor.value =
                                if (blurColor.value == white) black
                                else white
                        })
                    }
            )

            BlurText(
                color = textColor.value,
                blurColor = blurColor.value,
                blurRadius = 4.dp,
                fontSize = 18.sp,
                fontFamily = Fonts.migmix,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                text = todayText.value,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun PreviewMainContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
    ) {
        val time = mutableStateOf("12:34:56")
        val date = mutableStateOf("2022年12月06日")
        MainContent(null, time, date)
    }
}