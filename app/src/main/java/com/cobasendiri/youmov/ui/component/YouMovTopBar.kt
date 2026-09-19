package com.cobasendiri.youmov.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cobasendiri.youmov.R
import com.cobasendiri.youmov.ui.theme.DarkSurface
import com.cobasendiri.youmov.ui.theme.YouMovTheme

@Composable
fun YouMovTopBar(
    modifier: Modifier = Modifier,
    titleText: String,
    backgroundColor: Color = DarkSurface,
    endActions: @Composable () -> Unit = {},
    onNavigateBack: (() -> Unit)? = null
) {

    Row(modifier
        .fillMaxWidth()
        .background(backgroundColor)
        .statusBarsPadding()
        .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        onNavigateBack?.let {
            IconButton(it) {
                Image(
                    painter = painterResource(R.drawable.ic_arrow_left_32),
                    contentDescription = null
                )
            }
        }
        Spacer(Modifier.width(16.dp))
        Text(
            text = titleText,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(Modifier.weight(1f))
        endActions.invoke()
    }
}

@Preview(showBackground = true)
@Composable
fun YouMovTopBarPrev() {
    YouMovTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            YouMovTopBar(
                modifier = Modifier,
                titleText = "YouMov",
                endActions = {
                    IconButton({}) {
                        Image(
                            painter = painterResource(R.drawable.ic_favorite_filled_32),
                            contentDescription = null
                        )
                    }
                },
                onNavigateBack = null
            )
            YouMovTopBar(
                modifier = Modifier,
                titleText = "Favorite",
                endActions = {},
                onNavigateBack = { }
            )
            YouMovTopBar(
                modifier = Modifier,
                backgroundColor = DarkSurface.copy(alpha = 0.3f),
                titleText = "Movie Name",
                endActions = {},
                onNavigateBack = { }
            )
        }
    }
}