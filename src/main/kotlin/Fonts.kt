import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.fontFamily

object Fonts {
    @Composable
    fun migmix() = fontFamily(
        font(
            "MigMix Mono",
            "migmix-1m-regular",
            FontWeight.Normal,
            FontStyle.Normal
        ),

        font(
            "MigMix Mono",
            "migmix-1m-bold",
            FontWeight.Bold,
            FontStyle.Normal
        )
    )
}