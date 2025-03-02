package com.sustenanceandroid.composables.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sustenanceandroid.R
import com.sustenanceandroid.composables.TextStyles
import com.sustenanceandroid.model.Exposition
import com.sustenanceandroid.model.ScoreData
import com.sustenanceandroid.model.ScoreInfo

@Composable
fun HomeScreen() {
    /*
    *
    * Temporary data until api model gets build out,
    * also so that we have something to show visually
    * since the api endpoint is not ready yet.
    * We will remove this once everything is ready.
    *
    */
    val scoreData = ScoreData().apply {
        data = listOf(300f, 500f, 100f)
        scoreInfo = listOf(
            ScoreInfo().apply {
                score = "300"
                scoreFactor = "(+19)"
                category = "Thing #1"
            },
            ScoreInfo().apply {
                score = "500"
                scoreFactor = "(+22)"
                category = "Thing #2"
            },
            ScoreInfo().apply {
                score = "100"
                scoreFactor = "(+67)"
                category = "Thing #3"
            }
        )
    }
    val expositions: List<Exposition> = listOf(scoreData)

    Box {
        Column {
            HomeTopPanel("Lime")

            LazyColumn(
                contentPadding = PaddingValues(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                items(expositions) { exposition ->
                    when (exposition) {
                        is ScoreData -> SustainScore(exposition)
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeTopPanel(username: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 30.dp,
                start = 16.dp,
                end = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = stringResource(R.string.profile_picture),
            modifier = Modifier
                .shadow(4.dp, CircleShape)
                .size(40.dp)
                .clip(CircleShape)
        )

        Text(
            text = stringResource(R.string.greeting, username),
            style = TextStyles.panelText()
        )

        Box(
            modifier = Modifier
                .shadow(4.dp, CircleShape)
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.White)
        ) {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = stringResource(R.string.notifications),
                    tint = Color.Black
                )
            }
        }
    }
}
