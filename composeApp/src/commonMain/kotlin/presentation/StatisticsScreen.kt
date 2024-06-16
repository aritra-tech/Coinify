package presentation

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import component.LoadingDialog
import data.remote.Resources
import domain.model.Data
import domain.model.Listings
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
    when(latestListingState) {
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
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Top Movers",
                fontSize = 20.sp,
                textAlign = TextAlign.Start
            )
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
                        TopMovers(data)
                    }
                }
            }
        }
    }
}

@Composable
fun TopMovers(data: Data) {

    val percentage24h = data.quote.USD.percentChange24h
    val textColor24h = if (percentage24h > 0) {
        Color.Green
    } else {
        Color.Red
    }

    Card(
        modifier = Modifier
            .width(180.dp)
            .height(100.dp)
            .shadow(5.dp, RoundedCornerShape(18.dp))
            .clickable {  },

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowUpward,
                    contentDescription = "Gain",
                    tint = textColor24h,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "${percentage24h.roundToInt()}%",
                    color = textColor24h,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "${data.symbol} $" + "${((data.quote.USD.price?.times(100))?.roundToInt())?.div(
                        100.0
                    )}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = data.name,
                    fontSize = 12.sp,
                )
            }
        }
    }
}
