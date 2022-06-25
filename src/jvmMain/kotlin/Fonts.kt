import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font

object Fonts {
    val migmix = FontFamily(
        Font(
            "font/migmix-1m-regular.ttf",
            FontWeight.Normal,
            FontStyle.Normal
        ),

        Font(
            "font/migmix-1m-bold.ttf",
            FontWeight.Bold,
            FontStyle.Normal
        )
    )
}