package component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coinify.composeapp.generated.resources.Res
import coinify.composeapp.generated.resources.poppins_medium
import coinify.composeapp.generated.resources.poppins_regular
import domain.model.crypto.Data
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.Font
import kotlin.math.roundToInt

@Composable
fun CryptoCard(
    data: Data,
    onClick: (String) -> Unit
) {

    val percentChange24h = data.quote.USD.percentChange24h
    val textColor24h = if (percentChange24h > 0) Color.Green else Color.Red
    val jsonData = Json.encodeToString(data)
    val icon = if (percentChange24h > 0) {
        Icons.Default.ArrowUpward
    } else {
        Icons.Default.ArrowDownward
    }

    val iconColor = if (percentChange24h > 0) {
        Color.Green
    } else {
        Color.Red
    }

    Column(
        modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .clickable { onClick(jsonData) }
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier.size(40.dp),
                model = "https://s2.coinmarketcap.com/static/img/coins/64x64/${data.id}.png",
                contentDescription = null,
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = data.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(Res.font.poppins_medium))
                    )
                )

                Text(
                    text = data.symbol ?: "",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(Res.font.poppins_regular))
                    )
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "$" + "${((data.quote.USD.price?.times(100))?.roundToInt())?.div(100.0)}",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(Res.font.poppins_medium))
                    )
                )

                Row {
                    Text(
                        text = "${percentChange24h.roundToInt()}%",
                        color = textColor24h
                    )

                    Icon(
                        icon,
                        contentDescription = "Back button",
                        tint = iconColor
                    )
                }

            }

        }
    }
}
