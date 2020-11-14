import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
fun font(name: String, res: String, weight: FontWeight, style: FontStyle) : Font =
    androidx.compose.ui.text.platform.font(name, "font/$res.ttf", weight, style)