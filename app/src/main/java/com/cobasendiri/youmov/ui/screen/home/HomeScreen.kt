package com.cobasendiri.youmov.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.cobasendiri.youmov.R
import com.cobasendiri.youmov.domain.model.Movie
import com.cobasendiri.youmov.ui.ViewModelFactory
import com.cobasendiri.youmov.ui.component.YouMovTopBar
import com.cobasendiri.youmov.ui.theme.DarkBackground
import com.cobasendiri.youmov.ui.theme.YouMovTheme
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun HomeScreen(
    onNavigateToFavorite: () -> Unit,
    onNavigateToDetail: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            YouMovTopBar(
                titleText = stringResource(R.string.app_name),
                endActions = {
                    IconButton(modifier = Modifier.size(28.dp), onClick = onNavigateToFavorite) {
                        Image(
                            painter = painterResource(R.drawable.ic_favorite_filled_32),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ){ innerPadding ->

        val context = LocalContext.current
        val appContext = context.applicationContext

        val viewModel: HomeViewModel = viewModel(
            factory = ViewModelFactory.getInstance(appContext)
        )

        val popularMovies = viewModel.popularMovies.collectAsLazyPagingItems()
        val topRatedMovies = viewModel.topRatedMovies.collectAsLazyPagingItems()
        val nowPlayingMovies = viewModel.nowPlayingMovies.collectAsLazyPagingItems()

        HomeScreenContent(
            innerPadding,
            popularMovies = popularMovies,
            topRatedMovies = topRatedMovies,
            nowPlayingMovies = nowPlayingMovies
        ) { event ->
            when(event){
                is HomeScreenEvent.OnMovieClicked -> {
                    onNavigateToDetail.invoke(event.movieId)
                }
                is HomeScreenEvent.OnPopularRefreshClicked -> {
                    popularMovies.refresh()
                }
                is HomeScreenEvent.OnPopularRetryClicked ->{
                    popularMovies.retry()
                }
                is HomeScreenEvent.OnTopRatedRefreshClicked -> {
                    topRatedMovies.refresh()
                }
                is HomeScreenEvent.OnTopRatedRetryClicked->{
                    topRatedMovies.retry()
                }
                is HomeScreenEvent.OnNowPlayingRefreshClicked -> {
                    nowPlayingMovies.refresh()
                }
                is HomeScreenEvent.OnNowPlayingRetryClicked ->{
                    nowPlayingMovies.retry()
                }
                is HomeScreenEvent.OnFavoriteMenuClicked -> {
                    onNavigateToFavorite.invoke()
                }
            }
        }
    }
}

@Composable
fun HomeScreenContent(
    innerPadding: PaddingValues,
    popularMovies: LazyPagingItems<Movie>,
    topRatedMovies: LazyPagingItems<Movie>,
    nowPlayingMovies: LazyPagingItems<Movie>,
    event: (HomeScreenEvent) -> Unit
){
    val scrollState = rememberScrollState()

    Column(Modifier.fillMaxSize()
        .verticalScroll(scrollState)
        .background(DarkBackground)
        .padding(innerPadding)
        .padding(vertical = 16.dp)
        .padding(start = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.popular_movies),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(8.dp))
        PagingLandscapeMovieList(
            moviePagingItems = popularMovies,
            onItemClick = {
                event.invoke(HomeScreenEvent.OnMovieClicked(it))
            },
            onRefreshClick = {
                event.invoke(HomeScreenEvent.OnPopularRefreshClicked)
            },
            onRetryClick = {
                event.invoke(HomeScreenEvent.OnPopularRetryClicked)
            }
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.top_rated),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(8.dp))
        PagingPortraitMovieList(
            moviePagingItems = topRatedMovies,
            onItemClick = {
                event.invoke(HomeScreenEvent.OnMovieClicked(it))
            },
            onRefreshClick = {
                event.invoke(HomeScreenEvent.OnTopRatedRefreshClicked)
            },
            onRetryClick = {
                event.invoke(HomeScreenEvent.OnTopRatedRetryClicked)
            }
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.now_showing),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(8.dp))
        PagingPortraitMovieList(
            moviePagingItems = nowPlayingMovies,
            onItemClick = {
                event.invoke(HomeScreenEvent.OnMovieClicked(it))
            },
            onRefreshClick = {
                event.invoke(HomeScreenEvent.OnNowPlayingRefreshClicked)
            },
            onRetryClick = {
                event.invoke(HomeScreenEvent.OnNowPlayingRetryClicked)
            }
        )
    }
}

@Preview
@Composable
fun HomeScreenContentPrev() {
    val dummyPaging = MutableStateFlow(PagingData.from(
        listOf(
            Movie(1,"", "Movie One", "2024"),
            Movie(2,"", "Movie Two", "2022"),
            Movie(3,"", "Movie Three", "2020")
        )
    ))
    val dummy = dummyPaging.collectAsLazyPagingItems()

    YouMovTheme {
        HomeScreenContent(
            innerPadding = PaddingValues(0.dp),
            dummy,dummy,dummy
        ){}
    }
}