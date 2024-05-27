package presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import component.CoinChart
import domain.model.Data
import navigation.LocalNavHost
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(data: Data) {

    val navController = LocalNavHost.current
    val capMarketChanged24h = data.quote.USD.percentChange24h ?: 0.0
    var selectedDuration by remember { mutableStateOf("1h") }
    val duration = remember { mutableListOf("1h", "1d", "1w") }
    val textColor24h = if (capMarketChanged24h > 0) Color.Green else Color.Red

    Scaffold(
        topBar = {
            TopAppBar(
                title = { "Trade ${data.name}" },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back button")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = Modifier.size(36.dp),
                    model = "https://s2.coinmarketcap.com/static/img/coins/64x64/${data.id}.png",
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = data.symbol ?: "",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 16.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$" + "${((data.quote.USD.price?.times(100))?.roundToInt())?.div(100.0)}",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "${capMarketChanged24h}%",
                    style = TextStyle(
                        color = textColor24h,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            TabRow(
                modifier = Modifier.fillMaxWidth(),
                selectedTabIndex = duration.indexOf(selectedDuration),
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = Color.Black,
                indicator = { position ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(
                            position[duration.indexOf(
                                selectedDuration
                            )]
                        )
                    )
                }
            ) {
                duration.forEachIndexed { _, period ->
                    Tab(
                        selected = selectedDuration == period,
                        onClick = { selectedDuration = period },
                        text = { Text(text = period) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                when(selectedDuration) {
                    "1h" -> { CoinChart(data, "1h") }

                    "1d" -> { CoinChart(data, "1d") }

                    "1w" -> { CoinChart(data, "1w") }
                }
            }
        }
    }
}


