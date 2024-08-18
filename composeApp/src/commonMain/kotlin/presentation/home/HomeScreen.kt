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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coinify.composeapp.generated.resources.Res
import coinify.composeapp.generated.resources.coinify
import coinify.composeapp.generated.resources.poppins_medium
import component.CryptoCard
import component.SearchBar
import data.remote.Resources
import domain.model.crypto.Listings
import navigation.LocalNavHost
import navigation.Screens
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinInject()
) {

    val navController = LocalNavHost.current
    val latestListingObserver by viewModel.latestListing.collectAsState()
    val searchQueryObserver by viewModel.searchQuery.collectAsState()
    val filteredListingsObserver by viewModel.filteredListing.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getLatestListing()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(
                    text = stringResource(Res.string.coinify),
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(Res.font.poppins_medium))
                ) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
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
                search = searchQueryObserver,
                onValueChange = {
                    viewModel.updateSearchData(it)
                }
            ) {}

            Spacer(modifier = Modifier.height(20.dp))

            if (isLoading) {
                LazyColumn(
                    modifier = Modifier.weight(9f).background(MaterialTheme.colorScheme.background),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(10) {
                        HomeScreenShimmer()
                    }
                }
            } else {
                when (latestListingObserver) {
                    is Resources.SUCCESS -> {
                        LazyColumn(
                            modifier = Modifier.weight(9f).background(MaterialTheme.colorScheme.background),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(filteredListingsObserver) { data ->
                                CryptoCard(
                                    data
                                ) {
                                    navController.navigate("${Screens.Details.route}/$it")
                                }
                            }
                        }
                    }
                    is Resources.ERROR -> {
                        Text("Error loading data", color = MaterialTheme.colorScheme.error)
                    }

                    else -> {}
                }
            }
        }
    }
}
