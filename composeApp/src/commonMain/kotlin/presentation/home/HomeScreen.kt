package presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import component.CryptoCard
import data.remote.Resources
import domain.model.Data
import domain.model.Listings
import org.koin.compose.koinInject
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = koinInject()
) {

    var listingData by remember { mutableStateOf<Listings?>(null) }
    val latestListingObserver by homeViewModel.latestListing.collectAsState()

    when(latestListingObserver) {
        is Resources.ERROR -> {

        }

        is Resources.LOADING -> {

        }

        is Resources.SUCCESS -> {
            val data = (latestListingObserver as Resources.SUCCESS).response
            listingData = data
        }
    }

    LaunchedEffect(Unit) {
        homeViewModel.getLatestListing()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { "Coinify" },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                actions = {

                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier.fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            listingData?.data?.let { dataList ->
                LazyColumn(
                    modifier = Modifier.weight(9f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(dataList) {data ->
                        CryptoCard(data)
                    }
                }
            }
        }
    }
}
