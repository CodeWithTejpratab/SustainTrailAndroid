package com.sustenanceandroid.composables.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sustenanceandroid.composables.TextStyles
import com.sustenanceandroid.composables.URLImage
import com.sustenanceandroid.model.AdPlus

@Composable
fun AdScreen(adPlus: AdPlus) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(6.dp)
    ) {
        Text(
            text = adPlus.header.orEmpty(),
            style = TextStyles.headLine1()
        )

        Text(
            text = adPlus.category.orEmpty(),
            style = TextStyles.normalTextSmall().copy(color = Color(0xFFE67E22))
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(adPlus.images.orEmpty()) { imageUrl ->
                URLImage(
                    imageUrl = imageUrl,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .fillMaxHeight()
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {

            Text(
                text = "Sponsored Content",
                style = TextStyles.normalTextSmall().copy(color = Color.LightGray)
            )
        }
    }
}

@Preview
@Composable
fun AddScreenPreview() {
    val adPlus = AdPlus().apply {
        header = "Making the Most of Outdoor Space for a Bountiful and Beautiful Vegetable Garden"
        category = "Nature Channel"
        images = listOf(
            "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/1015f/MainBefore.jpg",
            "https://images.pexels.com/photos/1054655/pexels-photo-1054655.jpeg?cs=srgb&dl=pexels-hsapir-1054655.jpg&fm=jpg",
            "https://cdn.pixabay.com/photo/2024/05/26/10/15/bird-8788491_1280.jpg"
        )
    }

    AdScreen(adPlus)
}
