package com.sustenanceandroid.composables

import android.widget.ImageView
import android.widget.ImageView.ScaleType
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

@Composable
fun URLImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    imageScaleType: ScaleType = ScaleType.CENTER_CROP
) {
    val context = LocalContext.current

    AndroidView(
        factory = { ctx ->
            ImageView(ctx).apply {
                scaleType = imageScaleType
            }
        },
        update = { imageView ->
            Glide.with(context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
        },
        modifier = modifier
    )
}

@Preview
@Composable
private fun URLImagePreview() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        URLImage(
            imageUrl = "https://www.originsustainables.com/wp-content/uploads/2023/09/positive-climate-news-article-1-1024x538.jpg",
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)
        )
    }
}
