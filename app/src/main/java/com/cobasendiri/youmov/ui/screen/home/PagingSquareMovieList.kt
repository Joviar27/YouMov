package com.cobasendiri.youmov.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.cobasendiri.youmov.model.Movie
import com.cobasendiri.youmov.ui.component.LargeBannerItem
import com.cobasendiri.youmov.ui.component.LoadingIndicator
import com.cobasendiri.youmov.ui.component.LoadingMoreItem
import com.cobasendiri.youmov.ui.component.RefreshButton
import com.cobasendiri.youmov.ui.component.RefreshItem
import com.cobasendiri.youmov.ui.component.SmallBannerItem
import com.cobasendiri.youmov.ui.theme.YouMovTheme
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun PagingSquareMovieList(
    modifier: Modifier = Modifier,
    moviePagingItems: LazyPagingItems<Movie>,
    onItemClick: () -> Unit,
    onRefreshClick: () -> Unit
) {
    val lazyListState = rememberLazyListState()

    val refreshLoading = moviePagingItems.loadState.refresh is LoadState.Loading
    val refreshError = moviePagingItems.loadState.refresh is LoadState.Error
    val appendLoading = moviePagingItems.loadState.append is LoadState.Loading
    val appendError = moviePagingItems.loadState.append is LoadState.Error

    LaunchedEffect(refreshLoading){
        if(refreshLoading) lazyListState.scrollToItem(0)
    }

    Box(modifier.fillMaxWidth()
        .height(210.dp)
    ) {
        if(refreshLoading){
            LoadingIndicator(Modifier.size(48.dp))
        }
        if(refreshError){
            RefreshButton(
                Modifier.align(Alignment.Center),
                onRefreshClick
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            state = lazyListState
        ) {
            items(moviePagingItems.itemCount) { index ->
                moviePagingItems[index]?.let {
                    SmallBannerItem(
                        title = it.title,
                        imageUri = it.imageUri,
                        releaseInfo = it.releaseInfo,
                        onClick = onItemClick
                    )
                }
            }
            if (appendLoading) {
                item {
                    LoadingMoreItem()
                }
            }
            if (appendError){
                item {
                    RefreshItem(onClick = onRefreshClick)
                }
            }
        }
    }
}

@Preview
@Composable
fun PagingSquareMovieListPreview(){
    val dummyPaging = MutableStateFlow(PagingData.from(
        listOf(
            Movie("1","", "Movie One", "2024"),
            Movie("2","", "Movie Two", "2022"),
            Movie("3","", "Movie Three", "2020"),
            Movie("4","", "Movie Four", "2020")
        )
    ))
    val dummy = dummyPaging.collectAsLazyPagingItems()
    YouMovTheme {
        PagingSquareMovieList(
            moviePagingItems = dummy,
            onItemClick = {},
            onRefreshClick = {}
        )
    }
}