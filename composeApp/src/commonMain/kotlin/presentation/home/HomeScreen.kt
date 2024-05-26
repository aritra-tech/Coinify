package presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import component.CryptoCard
import component.LoadingDialog
import component.SearchBar
import data.remote.Resources
import domain.model.Listings
import navigation.LocalNavHost
import navigation.Screens
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinInject()
) {

    val navController = LocalNavHost.current

    var searchQuery by rememberSaveable { mutableStateOf("") }

    var listingData by remember { mutableStateOf<Listings?>(null) }

    val latestListingObserver by viewModel.latestListing.collectAsState()

    when(latestListingObserver) {
        is Resources.ERROR -> {}

        is Resources.LOADING -> {
            LoadingDialog()
        }

        is Resources.SUCCESS -> {
            val data = (latestListingObserver as Resources.SUCCESS).response
            listingData = data
        }

    }

    LaunchedEffect(Unit) {
        viewModel.getLatestListing()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { "Coinify" },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notification")
                    }
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

            SearchBar(
                search = searchQuery,
                onValueChange = { searchQuery = it }
            ) {

            }

            Spacer(modifier = Modifier.height(20.dp))

            listingData?.data?.let { dataList ->
                LazyColumn(
                    modifier = Modifier.weight(9f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(dataList) {data ->
                        CryptoCard(
                            data
                        ) {
                            navController.navigate("${Screens.Details.route}/$it")
                        }
                    }
                }
            }
        }
    }
}
