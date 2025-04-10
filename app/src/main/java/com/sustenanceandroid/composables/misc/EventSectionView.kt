package com.sustenanceandroid.composables.misc

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.sustenanceandroid.R
import com.sustenanceandroid.composables.TextStyles
import com.sustenanceandroid.composables.URLImage
import com.sustenanceandroid.model.Event
import com.sustenanceandroid.model.EventSection
import com.sustenanceandroid.module.Utils.formatTimestamp
import com.sustenanceandroid.ui.theme.EventsDateTextGray
import com.sustenanceandroid.ui.theme.EventsMainTextDarkBlue
import com.sustenanceandroid.ui.theme.EventsSpotsRemainingBlue
import com.sustenanceandroid.ui.theme.EventsSpotsRemainingGreen
import com.sustenanceandroid.ui.theme.EventsSpotsRemainingRed

@Composable
fun EventSectionView(events: EventSection) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.event_card_spacing)),
        modifier = Modifier.padding(dimensionResource(R.dimen.event_card_spacing))
    ) {
        events.events?.let { eventList ->
            items(eventList) { event ->
                EventCard(
                    imageUrl = event.imageUrl.orEmpty(),
                    title = event.title.orEmpty(),
                    url = event.url.orEmpty(),
                    date = event.date.orEmpty(),
                    totalSpots = event.totalSpots ?: 0,
                    remainingSpots = event.remainingSpots ?: 0
                )
            }
        }
    }
}

@Composable
private fun EventCard(
    imageUrl: String,
    title: String,
    url: String,
    date: String,
    totalSpots: Int,
    remainingSpots: Int
) {
    Card(
        shape = RoundedCornerShape(dimensionResource(R.dimen.event_card_corner_radius)),
        elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.event_card_box_shadow))
    ) {
        Column {
            URLImage(
                imageUrl = imageUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.event_card_image_height))
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                EventCardInformation(
                    title = title,
                    date = date,
                    url = url
                )

                RemainingSpotsDisplay(
                    totalSpots = totalSpots,
                    remainingSpots = remainingSpots
                )
            }
        }
    }
}

@Composable
private fun EventCardInformation(
    title: String,
    date: String,
    url: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.event_card_gutter)),
        modifier = Modifier.width(dimensionResource(R.dimen.event_card_information_max_width))
    ) {
        Text(
            text = title,
            style = TextStyles.eventTitleText(),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.padding(horizontal = 10.dp)
        )

        HorizontalDivider(thickness = 1.dp)

        DateEventDetails(date = date, url = url)
    }
}

@Composable
private fun DateEventDetails(
    date: String,
    url: String
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.event_card_gutter))
        ) {
            Icon(
                imageVector = Icons.Default.CalendarToday,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )

            Text(
                text = formatTimestamp(date),
                style = TextStyles.eventDateText().copy(color = EventsDateTextGray)
            )
        }

        SeeEventDetailsLink(url = url)
    }
}

@Composable
private fun SeeEventDetailsLink(url: String) {
    val context = LocalContext.current
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.clickable {
            val urlIntent = Intent(
                Intent.ACTION_VIEW,
                url.toUri()
            )
            context.startActivity(urlIntent)
        }
    ) {
        Text(
            text = stringResource(R.string.see_event_details),
            style = TextStyles.eventDetailsLinkText().copy(textDecoration = TextDecoration.Underline)
        )
        Text(
            text = stringResource(R.string.see_event_detail_arrow),
            style = TextStyles.eventDetailsLinkText(),
        )
    }
}

@Composable
private fun RemainingSpotsDisplay(
    totalSpots: Int,
    remainingSpots: Int
) {
    val spotsFilled = (totalSpots - remainingSpots).toFloat()
    val spotsFilledPct = spotsFilled / totalSpots
    val urgentColor = if (spotsFilledPct < 0.9) EventsSpotsRemainingGreen else EventsSpotsRemainingRed

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.event_card_gutter)),
        modifier = Modifier.fillMaxWidth()
    ) {
        CapacityProgressCircle(decimalPercent = spotsFilledPct)

        Text(
            text = "$remainingSpots ${stringResource(R.string.spots_left)}",
            style = TextStyles.eventRemainingSpotsText().copy(color = urgentColor)
        )
    }
}

@Composable
private fun CapacityProgressCircle(decimalPercent: Float) {
    Box(contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            progress = { decimalPercent },
            modifier = Modifier
                .size(dimensionResource(R.dimen.event_card_capacity_indicator_size))
                .scale(scaleX = -1f, scaleY = 1f),
            strokeWidth = 8.dp,
            color = EventsSpotsRemainingBlue,
            trackColor = Color.LightGray,
            strokeCap = StrokeCap.Square,
            gapSize = 0.dp
        )

        Text(
            text = "${(decimalPercent * 100).toInt()}%",
            style = TextStyles.eventCapacityPercentText().copy(color = EventsMainTextDarkBlue),
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EventSectionPreview() {
    val events = EventSection().apply {
        events = listOf(
            Event().apply {
                imageUrl = "https://scx1.b-cdn.net/csz/news/tmb/2025/sulfur-bacteria-team-u.jpg"
                title = "Cocoa’s Beach Cleanup"
                url = "google.com"
                date = "2025-03-07T18:09:33+00:00"
                totalSpots = 50
                remainingSpots = 25
            },
            Event().apply {
                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/f/f9/Phoenicopterus_ruber_in_S%C3%A3o_Paulo_Zoo.jpg"
                title = "Nagawe’s Village Cleanup"
                url = "facebook.com"
                date = "2025-04-02T18:09:33+00:00"
                totalSpots = 10
                remainingSpots = 0
            },
            Event().apply {
                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/f/f9/Phoenicopterus_ruber_in_S%C3%A3o_Paulo_Zoo.jpg"
                title = "Nagawe’s Village Cleanup"
                url = "facebook.com"
                date = "2025-04-02T18:09:33+00:00"
                totalSpots = 10
                remainingSpots = 0
            },
            Event().apply {
                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/f/f9/Phoenicopterus_ruber_in_S%C3%A3o_Paulo_Zoo.jpg"
                title = "Nagawe’s Village Cleanup"
                url = "facebook.com"
                date = "2025-04-02T18:09:33+00:00"
            }
        )
    }

    EventSectionView(events)
}
