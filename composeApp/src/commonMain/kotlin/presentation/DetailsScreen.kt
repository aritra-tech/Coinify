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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coinify.composeapp.generated.resources.Res
import coinify.composeapp.generated.resources.current_price
import coinify.composeapp.generated.resources.market_cap
import coinify.composeapp.generated.resources.max_supply
import coinify.composeapp.generated.resources.poppins_bold
import coinify.composeapp.generated.resources.poppins_extrabold
import coinify.composeapp.generated.resources.poppins_medium
import coinify.composeapp.generated.resources.poppins_regular
import coinify.composeapp.generated.resources.statistics
import coinify.composeapp.generated.resources.total_supply
import coinify.composeapp.generated.resources.volume_24h
import component.CoinChart
import domain.model.Data
import navigation.LocalNavHost
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import utils.formatData
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(data: Data) {

    val navController = LocalNavHost.current
    val capMarketChanged24h = data.quote.USD.percentChange24h
    var selectedDuration by remember { mutableStateOf("1HR") }
    val duration = remember { mutableListOf("1HR", "1D", "1W", "1M", "2M", "3M") }
    val textColor24h = if (capMarketChanged24h > 0) Color.Green else Color.Red

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(
                    text = data.name,
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(Res.font.poppins_bold))
                ) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back button")
                    }
                },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
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
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(Res.font.poppins_regular))
                    )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$" + "${((data.quote.USD.price?.times(100))?.roundToInt())?.div(100.0)}",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 24.sp,
                        fontFamily = FontFamily(Font(Res.font.poppins_extrabold))
                    )
                )

                Spacer(modifier = Modifier.weight(1f))

                Column {
                    Text(
                        text = "#${data.cmcRank}",
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 30.sp,
                            fontFamily = FontFamily(Font(Res.font.poppins_medium))
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "${formatData(capMarketChanged24h)}% (1d)",
                        style = TextStyle(
                            color = textColor24h,
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(Res.font.poppins_medium))
                        )
                    )
                }

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
                        text = { Text(
                            text = period,
                            color = MaterialTheme.colorScheme.onSurface
                        ) }
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
                    "1HR" -> { CoinChart(data, "1HR") }

                    "1D" -> { CoinChart(data, "1D") }

                    "1W" -> { CoinChart(data, "1W") }

                    "1M" -> { CoinChart(data, "1M") }

                    "2M" -> { CoinChart(data, "2M") }

                    "3M" -> { CoinChart(data, "3M") }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = stringResource(Res.string.statistics),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 22.sp,
                    fontFamily = FontFamily(Font(Res.font.poppins_medium))
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = stringResource(Res.string.current_price),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(Res.font.poppins_regular))
                    )
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "$" + "${((data.quote.USD.price?.times(100))?.roundToInt())?.div(100.0)}",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(Res.font.poppins_medium))
                    )
                )
            }

            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.surfaceDim
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = stringResource(Res.string.market_cap),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(Res.font.poppins_regular))
                    )
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = data.quote.USD.marketCap?.let { formatData(it) }.toString(),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(Res.font.poppins_medium))
                    )
                )
            }

            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.surfaceDim
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = stringResource(Res.string.volume_24h),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(Res.font.poppins_regular))
                    )
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = data.quote.USD.volumeChange24h?.let { formatData(it) }.toString(),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(Res.font.poppins_medium))
                    )
                )
            }

            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.surfaceDim
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = stringResource(Res.string.max_supply),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(Res.font.poppins_regular))
                    )
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = data.maxSupply.toString(),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(Res.font.poppins_medium))
                    )
                )
            }

            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.surfaceDim
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = stringResource(Res.string.total_supply),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(Res.font.poppins_regular))
                    )
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = data.totalSupply.toString(),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(Res.font.poppins_medium))
                    )
                )
            }
        }
    }
}


