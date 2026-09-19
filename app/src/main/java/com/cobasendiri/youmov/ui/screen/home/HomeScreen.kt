package com.cobasendiri.youmov.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.cobasendiri.youmov.R
import com.cobasendiri.youmov.model.Movie
import com.cobasendiri.youmov.ui.component.YouMovTopBar
import com.cobasendiri.youmov.ui.theme.DarkBackground
import com.cobasendiri.youmov.ui.theme.YouMovTheme
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun HomeScreen(
    onNavigateToFavorite: () -> Unit
) {
    Scaffold(
        topBar = {
            YouMovTopBar(
                titleText = stringResource(R.string.app_name),
                endActions = {
                    IconButton(onNavigateToFavorite) {
                        Image(
                            painter = painterResource(R.drawable.ic_favorite_filled_32),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ){ innerPadding ->

        val dummyPaging = MutableStateFlow(PagingData.from(
            listOf(
                Movie("1","", "Movie One", "2024"),
                Movie("2","", "Movie Two", "2022"),
                Movie("3","", "Movie Three", "2020")
            )
        ))
        val dummy = dummyPaging.collectAsLazyPagingItems()

        HomeScreenContent(
            innerPadding,
            popularMovies = dummy,
            topRatedMovies = dummy,
            nowPlayingMovies =dummy
        ) { event ->
            when(event){
                is HomeScreenEvent.OnMovieClicked -> {

                }
                is HomeScreenEvent.OnRefreshClicked -> {

                }
                is HomeScreenEvent.OnFavoriteMenuClicked -> {

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
    Column(Modifier.fillMaxSize()
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
                event.invoke(HomeScreenEvent.OnMovieClicked(""))
            },
            onRefreshClick = {
                event.invoke(HomeScreenEvent.OnRefreshClicked)
            }
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.top_rated),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(8.dp))
        PagingSquareMovieList(
            moviePagingItems = popularMovies,
            onItemClick = {
                event.invoke(HomeScreenEvent.OnMovieClicked(""))
            },
            onRefreshClick = {
                event.invoke(HomeScreenEvent.OnRefreshClicked)
            }
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.now_showing),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(8.dp))
        PagingSquareMovieList(
            moviePagingItems = popularMovies,
            onItemClick = {
                event.invoke(HomeScreenEvent.OnMovieClicked(""))
            },
            onRefreshClick = {
                event.invoke(HomeScreenEvent.OnRefreshClicked)
            }
        )
    }
}

@Preview
@Composable
fun HomeScreenContentPrev() {
    val dummyPaging = MutableStateFlow(PagingData.from(
        listOf(
            Movie("1","", "Movie One", "2024"),
            Movie("2","", "Movie Two", "2022"),
            Movie("3","", "Movie Three", "2020")
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

@Preview
@Composable
fun HomeScreenPrev(){
    YouMovTheme {
        HomeScreen {  }
    }
}