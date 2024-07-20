package presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coinify.composeapp.generated.resources.Res
import coinify.composeapp.generated.resources.market_trends
import coinify.composeapp.generated.resources.poppins_medium
import coinify.composeapp.generated.resources.poppins_regular
import coinify.composeapp.generated.resources.top_losing
import coinify.composeapp.generated.resources.top_movers
import component.StatsCard
import data.remote.Resources
import domain.model.Listings
import navigation.LocalNavHost
import navigation.Screens
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import kotlin.math.roundToInt

@Composable
fun StatisticsScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinInject()
) {
    val navController = LocalNavHost.current
    var data by remember { mutableStateOf<Listings?>(null) }
    LaunchedEffect(Unit) {
        viewModel.getLatestListing()
    }

    val latestListingState by viewModel.latestListing.collectAsState()
    when (latestListingState) {
        is Resources.ERROR -> {}

        is Resources.LOADING -> {}

        is Resources.SUCCESS -> {
            val response = (latestListingState as Resources.SUCCESS).response
            data = response
        }
    }

    Scaffold(
        topBar = {}
    ) { paddingValues ->

        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = stringResource(Res.string.market_trends),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(Res.font.poppins_medium))
                ),
                textAlign = TextAlign.Start,
            )

            Spacer(modifier = Modifier.height(15.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {

                val randomCurrency = data?.data?.random()
                randomCurrency?.let { data ->
                    val textColor =
                        if (data.quote.USD.percentChange24h > 0) Color.Green else Color.Red

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = "${data.name} (${data.symbol})",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = FontFamily(Font(Res.font.poppins_medium))
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "$ " + "${((data.quote.USD.price?.times(100))?.roundToInt())?.div(100.0)}",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontFamily = FontFamily(Font(Res.font.poppins_regular))
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = if (data.quote.USD.percentChange24h > 0) "▲" else "▼",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontFamily = FontFamily(Font(Res.font.poppins_regular))
                                )
                            )
                            Spacer(modifier = Modifier.width(4.dp))

                            Text(
                                text = "${data.quote.USD.percentChange24h.roundToInt()}% in 24h",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontFamily = FontFamily(Font(Res.font.poppins_regular))
                                ),
                                color = textColor
                            )
                        }
                    }
                }
            }

            // TOP MOVERS

            Text(
                text = stringResource(Res.string.top_movers),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(Res.font.poppins_medium))
                ),
                textAlign = TextAlign.Start,
            )

            Spacer(modifier = Modifier.height(15.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val topMovers = data?.data?.filter {
                    it.quote.USD.percentChange24h >= 0
                }?.sortedByDescending { it.quote.USD.percentChange24h }

                topMovers?.let { list ->
                    items(list) { data ->
                        StatsCard(data) {
                            navController.navigate("${Screens.Details.route}/$it")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // TOP LOSING

            Text(
                text = stringResource(Res.string.top_losing),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(Res.font.poppins_medium))
                ),
                textAlign = TextAlign.Start,
            )

            Spacer(modifier = Modifier.height(15.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val losingData = data?.data
                    ?.filter { it.quote.USD.percentChange24h < 0 }
                    ?.sortedBy { it.quote.USD.percentChange24h }
                losingData?.let { list ->
                    items(list) { data ->
                        StatsCard(data) {
                            navController.navigate("${Screens.Details.route}/$it")
                        }
                    }
                }
            }

        }
    }
}
