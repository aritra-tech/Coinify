package component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.baseComponents.model.GridOrientation
import com.aay.compose.baseComponents.model.LegendPosition
import com.aay.compose.lineChart.LineChart
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType
import domain.model.Data
import kotlin.math.abs
import kotlin.random.Random

@Composable
fun CoinChart(data: Data, selectedDuration: String) {
    val durationMappingData = when(selectedDuration) {
        "1HR" -> listOf(
            data.quote.USD.percentChange1h,
            data.quote.USD.percentChange24h,
            data.quote.USD.percentChange7d,
            data.quote.USD.percentChange30d,
            data.quote.USD.percentChange60d,
            data.quote.USD.percentChange90d,
        )

        "1D" -> listOf(
            Random.nextDouble(0.0,7.0),
            data.quote.USD.percentChange24h,
            data.quote.USD.percentChange7d,
            data.quote.USD.percentChange30d,
            data.quote.USD.percentChange60d,
            data.quote.USD.percentChange90d,
        )

        "1W" -> listOf(
            Random.nextDouble(0.0,7.0),
            Random.nextDouble(0.0,7.0),
            data.quote.USD.percentChange7d,
            data.quote.USD.percentChange30d,
            data.quote.USD.percentChange60d,
            data.quote.USD.percentChange90d,
        )

        "1M" -> listOf(
            Random.nextDouble(0.0,7.0),
            Random.nextDouble(0.0,7.0),
            Random.nextDouble(0.0,7.0),
            data.quote.USD.percentChange30d,
            data.quote.USD.percentChange60d,
            data.quote.USD.percentChange90d,
        )

        "2M" -> listOf(
            Random.nextDouble(0.0,7.0),
            Random.nextDouble(0.0,7.0),
            Random.nextDouble(0.0,7.0),
            Random.nextDouble(0.0,7.0),
            data.quote.USD.percentChange60d,
            data.quote.USD.percentChange90d,
        )

        "3M" -> listOf(
            Random.nextDouble(0.0,7.0),
            Random.nextDouble(0.0,7.0),
            Random.nextDouble(0.0,7.0),
            Random.nextDouble(0.0,7.0),
            Random.nextDouble(0.0,7.0),
            data.quote.USD.percentChange90d,
        )

        else -> emptyList()
    }

    val durationList = mutableListOf<Double>()
    durationList.add(data.quote.USD.percentChange1h)
    durationList.add(data.quote.USD.percentChange24h)
    durationList.add(data.quote.USD.percentChange7d)
    durationList.add(data.quote.USD.percentChange30d)
    durationList.add(data.quote.USD.percentChange60d)
    durationList.add(data.quote.USD.percentChange90d)
    val listOfData = durationMappingData.map { abs(it) }

    val testLineParameters: List<LineParameters> = listOf(
        LineParameters(
            label = "Price",
            data = listOfData,
            lineColor = Color.Green,
            lineType = LineType.CURVED_LINE,
            lineShadow = false
        )
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LineChart(
            modifier = Modifier.fillMaxWidth()
                .height(240.dp),
            linesParameters = testLineParameters,
            isGrid = false,
            gridColor = Color.LightGray,
            animateChart = true,
            showGridWithSpacer = false,
            legendPosition = LegendPosition.TOP,
            xAxisData = listOf(
                "2019",
                "2020",
                "2021",
                "2022",
                "2023",
                "2024"
            ),
            xAxisStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.Gray,
                fontWeight = FontWeight.W400
            ),
            yAxisRange = 6,
            oneLineChart = false,
            gridOrientation = GridOrientation.VERTICAL
        )
    }
}