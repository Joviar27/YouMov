package com.cobasendiri.youmov.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cobasendiri.youmov.R
import com.cobasendiri.youmov.ui.theme.DarkSurface

@Composable
fun RefreshButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Image(
            modifier = Modifier.background(DarkSurface)
                .padding(2.dp),
            painter = painterResource(R.drawable.ic_refresh_48),
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun RefreshButtonPrev() {
    RefreshButton(Modifier.size(32.dp)){}
}