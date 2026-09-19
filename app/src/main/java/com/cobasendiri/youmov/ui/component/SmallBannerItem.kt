package com.cobasendiri.youmov.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.cobasendiri.youmov.R
import com.cobasendiri.youmov.ui.theme.YouMovTheme

@Composable
fun SmallBannerItem(
    imageUri: String,
    title: String,
    releaseInfo: String,
    onClick: () -> Unit
) {
    Column(Modifier.width(200.dp)){
        AsyncImage(
            modifier = Modifier.size(200.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable(onClick = onClick),
            model = imageUri,
            placeholder = painterResource(R.drawable.placeholder_square),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = releaseInfo,
            style = MaterialTheme.typography.labelLarge
        )
    }

}

@Preview
@Composable
fun SmallBannerItemPrev() {
    YouMovTheme {
        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
            SmallBannerItem(
                imageUri = "",
                title = "Movie Title",
                releaseInfo = "19 Oktober 2024",
                onClick = { }
            )

            SmallBannerItem(
                imageUri = "",
                title = "Longer Movie Title That Maybe Like This Okay",
                releaseInfo = "19 Oktober 2024",
                onClick = { }
            )
        }
    }
}