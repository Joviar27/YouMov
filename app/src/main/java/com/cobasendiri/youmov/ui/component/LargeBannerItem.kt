package com.cobasendiri.youmov.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.cobasendiri.youmov.R
import com.cobasendiri.youmov.ui.theme.DarkBackground
import com.cobasendiri.youmov.ui.theme.DarkSurface
import com.cobasendiri.youmov.ui.theme.YouMovTheme

@Composable
fun LargeBannerItem(
    imageUri: String,
    title: String,
    releaseInfo: String,
    onClick: () -> Unit
) {
    Box(Modifier
        .size(width = 360.dp, height = 200.dp)
        .clip(RoundedCornerShape(16.dp))
        .clickable(onClick = onClick)
    ){
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = imageUri,
            placeholder = painterResource(R.drawable.placeholder_landscape),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Row(Modifier.fillMaxWidth()
            .background(DarkBackground.copy(alpha = 0.3f))
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .align(Alignment.BottomStart)
        ){
            Text(
                text = buildAnnotatedString {
                    withStyle(style = MaterialTheme.typography.titleMedium.toSpanStyle()){
                        append(title)
                    }
                    withStyle(style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Normal
                    ).toSpanStyle()){
                        append(" ($releaseInfo)")
                    }
                },
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun LargeBannerItemPrev() {
    YouMovTheme {
        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
            LargeBannerItem(
                imageUri = "",
                title = "Movie Title",
                releaseInfo = "2024",
                onClick = { }
            )

            LargeBannerItem(
                imageUri = "",
                title = "Longer Movie Title That Maybe Like This Okay",
                releaseInfo = "2024",
                onClick = { }
            )
        }
    }
}