/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.aritra.wearapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.material3.Card
import androidx.wear.compose.material3.CardDefaults
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Text
import coil3.compose.AsyncImage
import com.aritra.wearapp.R
import domain.model.crypto.Data
import org.koin.compose.koinInject
import presentation.home.HomeViewModel
import ui.CoinifyTheme
import kotlin.math.roundToInt

class WearActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            CoinifyTheme {
                CoinifyOS()
            }
        }
    }
}

@Composable
fun CoinifyOS(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinInject()
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val filteredListingsObserver by viewModel.filteredListing.collectAsState()
    val transition = rememberInfiniteTransition(label = "Loading animation")
    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(1000, easing = EaseIn),
            repeatMode = RepeatMode.Restart
        ),
        label = "Loading"
    )

    ScalingLazyColumn(
        modifier = modifier.background(MaterialTheme.colorScheme.background)
    ) {
        if (isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(35.dp)
                            .background(Color.White)
                    ) {
                        Image(
                            modifier = Modifier
                                .padding(5.dp)
                                .rotate(rotation),
                            painter = painterResource(id = R.drawable.loading),
                            contentDescription = ""
                        )
                    }
                }
            }
        } else {
            item {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Coinify",
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            items(filteredListingsObserver) { data ->
                CryptoItem(data)
            }
        }
    }
}

@Composable
fun CryptoItem(
    data: Data
) {
    val percentChange24h = data.quote.USD.percentChange24h
    val textColor24h = if (percentChange24h > 0) Color.Green else Color.Red

    Card(
        onClick = {},
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier.size(40.dp),
                model = "https://s2.coinmarketcap.com/static/img/coins/64x64/${data.id}.png",
                contentDescription = null,
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = data.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp,
                    )
                )

                Text(
                    text = data.symbol ?: "",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 12.sp,
                    )
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "$" + "${((data.quote.USD.price?.times(100))?.roundToInt())?.div(100.0)}",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp,
                    )
                )

                Row {
                    Text(
                        text = "${percentChange24h.roundToInt()}%",
                        color = textColor24h
                    )
                }

            }

        }
    }
}
