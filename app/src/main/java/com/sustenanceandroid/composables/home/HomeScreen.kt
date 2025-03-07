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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sustenanceandroid.R
import com.sustenanceandroid.composables.TextStyles
import com.sustenanceandroid.model.NewsSection
import com.sustenanceandroid.model.ScoreData
import com.sustenanceandroid.model.api.ExpositionRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(ExpositionRepository()))
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchExpositions()
    }

    Box {
        PullToRefreshBox(
            isRefreshing = uiState.isRefreshing,
            onRefresh = viewModel::fetchExpositions,
        ) {
            Column {
                HomeTopPanel()

                LazyColumn(
                    contentPadding = PaddingValues(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(uiState.expositions) { exposition ->
                        when (exposition) {
                            is ScoreData -> SustainScore(exposition)
                            is NewsSection -> NewsSectionView(exposition)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeTopPanel() {
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
            text = stringResource(R.string.greeting, "user"),
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
