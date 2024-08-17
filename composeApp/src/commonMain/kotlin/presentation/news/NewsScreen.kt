package presentation.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coinify.composeapp.generated.resources.Res
import coinify.composeapp.generated.resources.poppins_bold
import coinify.composeapp.generated.resources.poppins_medium
import coinify.composeapp.generated.resources.poppins_regular
import data.remote.Resources
import domain.model.news.News
import org.company.app.domain.model.news.Data
import org.jetbrains.compose.resources.Font
import org.koin.compose.koinInject
import utils.formatTimeStamp

@Composable
fun NewsScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = koinInject()
) {
    var newsList by remember { mutableStateOf<News?>(null) }
    val newsState by viewModel.allNews.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAllNews()
    }

    when(newsState) {
        is Resources.ERROR -> {

        }
        is Resources.LOADING -> {

        }

        is Resources.SUCCESS -> {
            val response = (newsState as Resources.SUCCESS).response
            newsList = response
        }
    }

    Scaffold(
        topBar = {}
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
            newsList?.data?.let { news ->
                LazyColumn(
                    modifier = Modifier.weight(9f).background(MaterialTheme.colorScheme.background),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(news) {
                        NewsCards(it)
                    }
                }
            }

        }
    }
}

@Composable
fun NewsCards(news: Data) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
            ) {

                AsyncImage(
                    modifier = Modifier.matchParentSize(),
                    model = news.imageurl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = news.body,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(Res.font.poppins_regular))
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = news.sourceInfo.img,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = news.sourceInfo.name,
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(Res.font.poppins_bold))
                        ),
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = " Published: ${formatTimeStamp(news.publishedOn.toLong())}",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(Res.font.poppins_medium))
                    )
                )
            }
        }
    }
}
