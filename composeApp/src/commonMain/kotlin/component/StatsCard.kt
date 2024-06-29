package component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import coinify.composeapp.generated.resources.Res
import coinify.composeapp.generated.resources.poppins_medium
import coinify.composeapp.generated.resources.poppins_regular
import domain.model.Data
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.Font
import kotlin.math.roundToInt

@Composable
fun StatsCard(
    data: Data,
    onClick: (String) -> Unit
) {
    val encodedData = remember(data) { Json.encodeToString(data) }
    val percentage24h = data.quote.USD.percentChange24h
    val textColor24h = if (percentage24h > 0) Color.Green else Color.Red

    Column(
        modifier = Modifier
            .width(200.dp)
            .height(130.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .clickable { onClick(encodedData) }
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = data.name,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = FontFamily(Font(Res.font.poppins_medium))
                    )
                )

                Spacer(modifier = Modifier.weight(1f))

                val formattedPrice = data.quote.USD.price?.times(100)?.roundToInt()?.div(100.0)
                Text(
                    text = "$ $formattedPrice",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = FontFamily(Font(Res.font.poppins_regular))
                    )
                )
            }

            Text(
                text = "${data.symbol}",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = FontFamily(Font(Res.font.poppins_regular))
                )
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                imageVector = Icons.Default.ArrowUpward,
                contentDescription = if (percentage24h > 0) "Gain" else "Loss",
                tint = textColor24h,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${percentage24h.roundToInt()}%",
                color = textColor24h,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = FontFamily(Font(Res.font.poppins_medium))
                )
            )
        }
    }
}