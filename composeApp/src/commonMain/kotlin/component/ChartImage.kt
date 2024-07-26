package component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage

@Composable
fun ChartImage(
    modifier: Modifier = Modifier,
    id: Int,
    tintColor: Color
) {

    AsyncImage(
        modifier = modifier,
        model = "https://s3.coinmarketcap.com/generated/sparklines/web/7d/2781/$id.svg",
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        colorFilter = ColorFilter.tint(color = tintColor)
    )
}