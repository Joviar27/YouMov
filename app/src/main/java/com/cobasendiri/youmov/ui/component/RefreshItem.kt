package com.cobasendiri.youmov.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cobasendiri.youmov.ui.theme.DarkBackground
import com.cobasendiri.youmov.ui.theme.YouMovTheme

@Composable
fun RefreshItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier.fillMaxHeight()
            .wrapContentWidth()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        RefreshButton(Modifier.size(32.dp), onClick)
    }
}

@Preview(showBackground = true)
@Composable
fun RefreshItemPrev() {
    YouMovTheme {
        Box(Modifier.height(200.dp)
            .background(DarkBackground)
        ){
            RefreshItem { }
        }
    }
}