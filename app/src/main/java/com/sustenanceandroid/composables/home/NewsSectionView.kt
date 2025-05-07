package com.sustenanceandroid.composables.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.sustenanceandroid.R
import com.sustenanceandroid.composables.TextStyles
import com.sustenanceandroid.composables.URLImage
import com.sustenanceandroid.model.Article
import com.sustenanceandroid.model.NewsSection
import com.sustenanceandroid.module.AppModule.apiRepository
import com.sustenanceandroid.module.Utils.formatTimestamp
import com.sustenanceandroid.module.Utils.launchCustomChromeTab
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun NewsSectionView(news: NewsSection) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(16.dp)) {
        HorizontalDivider(color = Color.Black, modifier = Modifier.padding(bottom = 12.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = news.header ?: "",
                style = TextStyles.headLine1()
            )

            Text(
                text = stringResource(R.string.view_all),
                color = Color(0xFFE67E22),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable { }
            )
        }

        LazyRow {
            news.articles?.let { articlesList ->
                items(articlesList) { article ->
                    NewsCard(article)
                }
            }
        }
    }
}

@Composable
private fun NewsCard(
    article: Article,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Card(
        shape = AbsoluteRoundedCornerShape(4.dp),
        elevation = cardElevation(6.dp),
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.White,
            disabledContainerColor = Color.White,
            disabledContentColor = Color.White
        ),
        modifier = modifier
            .width(300.dp)
            .height(290.dp)
            .padding(6.dp)
            .clickable {
                article.url?.let {
                    CoroutineScope(Dispatchers.Default).launch {
                        apiRepository.sendActionEvent("news")
                    }
                    launchCustomChromeTab(context, it.toUri())
                }
            }
    ) {
        Column {
            URLImage(
                imageUrl = article.imageURL.orEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(12.dp)
            ) {
                Text(
                    text = article.title.orEmpty(),
                    style = TextStyles.normalText1(),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Row {
                    Text(
                        text = article.category.orEmpty(),
                        style = TextStyles.normalTextSmall().copy(color = Color(0xFFE67E22))
                    )

                    Text(
                        text = " • ${formatTimestamp(article.publish.orEmpty())}",
                        style = TextStyles.normalTextSmall().copy(color = Color.Gray)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewNewsSection() {
    val sampleNews = NewsSection().apply {
        header = "Latest News"
        articles = listOf(
            Article().apply {
                title = "Climate change: Arctic warming linked to colder winters"
                category = "Nature"
                imageURL = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTjgXGxd7YfLOEreWSrN9lgOquoTZn6JY9UIw&s"
                publish = "2025-03-07T18:09:33+00:00"
                url = "Hello.com"
            },
            Article().apply {
                title = "Will the world wake up"
                category = "Nature"
                imageURL = "https://www.originsustainables.com/wp-content/uploads/2023/09/positive-climate-news-article-1-1024x538.jpg"
                publish = "2025-03-07T18:09:33+00:00"
            },
            Article().apply {
                title = "Tokyo Paralympics focus on sustainability"
                category = "Sports"
                imageURL = "https://thumbs.dreamstime.com/b/environment-news-newspaper-clipping-pinned-to-green-paper-background-33343837.jpg"
                publish = "2025-03-07T18:09:33+00:00"
            },
            Article().apply {
                title = "Tokyo Paralympics focus on sustainability"
                category = ""
                imageURL = "https://www.fairobserver.com/wp-content/uploads/2021/07/Climate-change-news.jpg"
                publish = "2025-03-07T18:09:33+00:00"
            },
            Article().apply {
                title = "Tokyo Paralympics focus on sustainability"
                category = "Sports"
                imageURL = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQZsjGNQYmL4dJ9isyXDYnodg2a3aIvYKqy8g&s"
                publish = "2025-03-07T18:09:33+00:00"
            }
        )
    }

    val sampleNews2 = NewsSection().apply {
        header = "Environmental News"
        articles = listOf(
            Article().apply {
                title = "Climate change: Arctic warming linked to colder winters"
                category = "Nature"
                imageURL = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTjgXGxd7YfLOEreWSrN9lgOquoTZn6JY9UIw&s"
                publish = "2025-03-07T18:09:33+00:00"
            },
            Article().apply {
                title = "Tokyo Paralympics focus on sustainability"
                category = "Sports"
                imageURL = "https://thumbs.dreamstime.com/b/environment-news-newspaper-clipping-pinned-to-green-paper-background-33343837.jpg"
                publish = "2025-03-07T18:09:33+00:00"
            },
            Article().apply {
                title = "Tokyo Paralympics focus on sustainability"
                category = "Sports"
                imageURL = "https://www.fairobserver.com/wp-content/uploads/2021/07/Climate-change-news.jpg"
                publish = "2025-03-07T18:09:33+00:00"
            },
            Article().apply {
                title = "Tokyo Paralympics focus on sustainability"
                category = "Sports"
                imageURL = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQZsjGNQYmL4dJ9isyXDYnodg2a3aIvYKqy8g&s"
                publish = "2025-03-07T18:09:33+00:00"
            }
        )
    }
    Column {
        NewsSectionView(sampleNews)
        NewsSectionView(sampleNews2)
    }
}
