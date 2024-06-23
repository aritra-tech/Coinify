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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coinify.composeapp.generated.resources.Res
import coinify.composeapp.generated.resources.poppins_medium
import coinify.composeapp.generated.resources.poppins_regular
import coinify.composeapp.generated.resources.top_losing
import coinify.composeapp.generated.resources.top_movers
import component.LoadingDialog
import data.remote.Resources
import domain.model.Data
import domain.model.Listings
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import kotlin.math.roundToInt

@Composable
fun StatisticsScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinInject()
) {
    var data by remember { mutableStateOf<Listings?>(null) }
    LaunchedEffect(Unit) {
        viewModel.getLatestListing()
    }

    val latestListingState by viewModel.latestListing.collectAsState()
    when (latestListingState) {
        is Resources.ERROR -> {}

        is Resources.LOADING -> {
            LoadingDialog()
        }

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
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
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
                        StatsCard(data)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

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
                        StatsCard(data)
                    }
                }
            }

        }
    }
}

@Composable
fun StatsCard(data: Data) {

    val percentage24h = data.quote.USD.percentChange24h
    val textColor24h = if (percentage24h > 0) {
        Color.Green
    } else {
        Color.Red
    }

    Column(
        modifier = Modifier
            .width(200.dp)
            .height(130.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .fillMaxSize()
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
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(Res.font.poppins_regular))
                    )
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "$ ${
                        ((data.quote.USD.price?.times(100))?.roundToInt())?.div(
                            100.0
                        )
                    }",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(Res.font.poppins_regular))
                    )
                )
            }

            Text(
                text = "${data.symbol}",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(Res.font.poppins_regular))
                )
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
        ) {
            Icon(
                imageVector = Icons.Default.ArrowUpward,
                contentDescription = "Gain",
                tint = textColor24h,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${percentage24h.roundToInt()}%",
                color = textColor24h,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(Res.font.poppins_medium))
                )
            )
        }

    }
}

