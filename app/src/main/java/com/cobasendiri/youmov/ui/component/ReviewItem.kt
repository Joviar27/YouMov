package com.cobasendiri.youmov.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.cobasendiri.youmov.BuildConfig
import com.cobasendiri.youmov.R
import com.cobasendiri.youmov.ui.theme.DarkBackground
import com.cobasendiri.youmov.ui.theme.DarkSurface
import com.cobasendiri.youmov.ui.theme.TextPrimary
import com.cobasendiri.youmov.ui.theme.YouMovTheme
import com.cobasendiri.youmov.ui.util.toReadableDate
import com.cobasendiri.youmov.ui.util.toReadableDateTime

@Composable
fun ReviewItem(
    modifier: Modifier = Modifier,
    avatarPath: String,
    reviewAuthor: String,
    reviewRating: String,
    reviewDate: String,
    reviewContent: String,
) {
    var isExpanded by remember { mutableStateOf(false) }
    var isOverflowing by remember { mutableStateOf(false) }

    val collapsedMaxLines = 5
    val profilePath = if(avatarPath=="null") "" else avatarPath

    Row(modifier.fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(DarkSurface)
        .padding(10.dp)
    ) {
        AsyncImage(
            modifier = Modifier.padding(end = 8.dp)
                .size(40.dp)
                .clip(CircleShape)
                .background(DarkBackground.copy(alpha = 0.6f)),
            model = "${BuildConfig.IMAGE_BASE_URL}$profilePath",
            placeholder = painterResource(R.drawable.ic_person),
            error = painterResource(R.drawable.ic_person),
            contentScale = ContentScale.Crop,
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
                if(reviewRating.isNotEmpty()){
                    Text(
                        text = "$reviewRating/10",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
            }
            Text(
                text = reviewDate.toReadableDateTime(),
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = reviewContent,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = if(isExpanded) Int.MAX_VALUE else collapsedMaxLines,
                onTextLayout = { textLayoutResult ->
                    if (!isExpanded) {
                        isOverflowing = textLayoutResult.hasVisualOverflow ||
                                textLayoutResult.lineCount > collapsedMaxLines
                    }
                }
            )
            if (isOverflowing || isExpanded) {
                Text(
                    text = if(isExpanded){
                        stringResource(R.string.show_less)
                    }else{
                        stringResource(R.string.show_more)
                    },
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 14.sp
                    ),
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            isExpanded = !isExpanded
                        }
                )
            }
        }
    }

}

@Preview
@Composable
fun ReviewItemPrev() {
    YouMovTheme{
        ReviewItem(
            avatarPath = "",
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