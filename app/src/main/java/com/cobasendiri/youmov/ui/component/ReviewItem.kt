package com.cobasendiri.youmov.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.cobasendiri.youmov.R
import com.cobasendiri.youmov.ui.theme.DarkBackground
import com.cobasendiri.youmov.ui.theme.DarkSurface
import com.cobasendiri.youmov.ui.theme.YouMovTheme

@Composable
fun ReviewItem(
    avatarUri: String,
    reviewAuthor: String,
    reviewRating: String,
    reviewDate: String,
    reviewContent: String
) {
    Row(Modifier.fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(DarkSurface)
        .padding(10.dp)
    ) {
        AsyncImage(
            modifier = Modifier.padding(end = 8.dp)
                .size(40.dp)
                .clip(CircleShape)
                .background(DarkBackground.copy(alpha = 0.6f)),
            model = avatarUri,
            placeholder = painterResource(R.drawable.ic_person),
            contentDescription = null
        )
        Column {
            Row(Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = reviewAuthor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "$reviewRating/10",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Normal
                    )
                )
            }
            Text(
                text = reviewDate,
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = reviewContent,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

}

@Preview
@Composable
fun ReviewItemPrev() {
    YouMovTheme{
        ReviewItem(
            avatarUri = "",
            reviewAuthor = "Author Name",
            reviewRating = "6.5",
            reviewDate = "21 Desember 2026",
            reviewContent = "Long text review of a moview, the author state" +
                    "their opinion in this long text, but apparently it's not" +
                    "enough to convince people otherwise, maybe we all just a spec" +
                    "of dust in this huge universe"
        )
    }
}