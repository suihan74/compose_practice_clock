import androidx.compose.desktop.AppManager
import androidx.compose.desktop.Window
import androidx.compose.desktop.WindowEvents
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.awt.image.BufferedImage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun main() {
    Window(
        title = "",
        icon = BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
        size = IntSize(380, 180),
        events = WindowEvents(
            onFocusGet = {
                // サイズ変更を禁止する
                AppManager.focusedWindow?.window?.let { jFrame ->
                    jFrame.isResizable = false
                }
            },
        )
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
                delay(1_000)
            }
        }

        // 画面レイアウト
        MaterialTheme {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                verticalArrangement = Arrangement.spacedBy((-10).dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    color = Color.White,
                    fontSize = 72.sp,
                    fontFamily = Fonts.migmix(),
                    text = currentTimeText.value,
                )

                Text(
                    color = Color.White,
                    fontSize = 18.sp,
                    fontFamily = Fonts.migmix(),
                    text = todayText.value
                )
            }
        }
    }
}