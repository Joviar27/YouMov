package com.cobasendiri.youmov.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
fun FavoriteItem(
    imageUri: String,
    title: String,
    releaseInfo: String,
    overview: String,
    onClick: () -> Unit
) {
    Row(Modifier.clickable(onClick = onClick)) {
        AsyncImage(
            modifier = Modifier
                .size(160.dp)
                .clip(RoundedCornerShape(16.dp)),
            model = imageUri,
            placeholder = painterResource(R.drawable.placeholder_square),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Column(Modifier.padding(16.dp)
            .align(Alignment.CenterVertically)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = releaseInfo,
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = overview,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview()
@Composable
fun FavoriteItemPrev() {
    YouMovTheme {
        FavoriteItem(
            imageUri = "",
            title = "Movie Title",
            releaseInfo = "19 Oktober 2024",
            overview = "The overview of the movie displayed in this item apparently can get quite long yeah it can get",
            onClick = { }
        )
    }
}