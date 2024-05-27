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
        "1h" -> listOf(
            data.quote.USD.percentChange1h,
            data.quote.USD.percentChange24h,
            data.quote.USD.percentChange7d,
        )

        "1d" -> listOf(
            Random.nextDouble(0.0,7.0),
            data.quote.USD.percentChange24h,
            data.quote.USD.percentChange7d,
        )

        "1w" -> listOf(
            Random.nextDouble(0.0,7.0),
            Random.nextDouble(0.0,7.0),
            data.quote.USD.percentChange7d,
        )

        else -> emptyList()
    }

    val durationList = mutableListOf<Double>()
    data.quote.USD.percentChange1h.let { durationList.add(it) }
    data.quote.USD.percentChange24h.let { durationList.add(it) }
    data.quote.USD.percentChange7d.let { durationList.add(it) }
    val listOfData = durationMappingData.map { abs(it) }

    val testLineParameters: List<LineParameters> = listOf(
        LineParameters(
            label = "",
            data = listOfData,
            lineColor = Color.Blue,
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
            modifier = Modifier.fillMaxWidth(),
            linesParameters = testLineParameters,
            isGrid = false,
            gridColor = Color.LightGray,
            animateChart = true,
            showGridWithSpacer = true,
            legendPosition = LegendPosition.TOP,
            xAxisData = listOf(
                "2022",
                "2023",
                "2024"
            ),
            yAxisStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.Gray,
            ),
            xAxisStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.Gray,
                fontWeight = FontWeight.W400
            ),
            yAxisRange = 3,
            oneLineChart = false,
            gridOrientation = GridOrientation.VERTICAL
        )
    }
}