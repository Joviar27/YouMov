package com.cobasendiri.youmov.ui.screen.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cobasendiri.youmov.R
import com.cobasendiri.youmov.domain.model.FavoriteMovie
import com.cobasendiri.youmov.ui.component.FavoriteItem
import com.cobasendiri.youmov.ui.component.YouMovTopBar
import com.cobasendiri.youmov.ui.theme.DarkBackground

@Composable
fun FavoriteScreen(
    onNavigateBack: () -> Unit
) {

    Scaffold(
        topBar = {
            YouMovTopBar(
                titleText = stringResource(R.string.favorite),
                onNavigateBack = onNavigateBack
            )
        }
    ) { innerPadding ->

        val dummyFavorite = listOf(
            FavoriteMovie("1","", "Movie One", "2024","overview of favorite overview of favorite overview of favorite overview of favorite"),
            FavoriteMovie("2","", "Movie Two", "2022","overview of favorite overview of favorite overview of favorite overview of favorite"),
            FavoriteMovie("3","", "Movie Three", "2020","overview of favorite overview of favorite overview of favorite overview of favorite")
        )

        FavoriteScreenContent(
            innerPadding = innerPadding,
            favoriteMovies = dummyFavorite
        ) { event ->
            when(event){
                is FavoriteScreenEvent.OnNavigateBack ->{
                    onNavigateBack.invoke()
                }
                is FavoriteScreenEvent.OnMovieClicked ->{

                }
            }
        }
    }

}

@Composable
fun FavoriteScreenContent(
    innerPadding: PaddingValues,
    favoriteMovies: List<FavoriteMovie>,
    event: (FavoriteScreenEvent) -> Unit
){
    LazyColumn(Modifier.fillMaxSize()
        .background(DarkBackground)
        .padding(innerPadding)
        .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(favoriteMovies.count()){ index ->
            favoriteMovies[index].let {
                FavoriteItem(
                    imageUri = it.imageUri,
                    title = it.title,
                    releaseInfo = it.releaseInfo,
                    overview = it.overview
                ) {
                    event.invoke(FavoriteScreenEvent.OnMovieClicked(""))
                }
            }
        }
    }
}

@Preview
@Composable
fun FavoriteScreenContentPrev() {

    val dummyFavorite = listOf(
        FavoriteMovie("1","", "Movie One", "2024","overview of favorite overview of favorite overview of favorite overview of favorite"),
        FavoriteMovie("2","", "Movie Two", "2022","overview of favorite overview of favorite overview of favorite overview of favorite"),
        FavoriteMovie("3","", "Movie Three", "2020","overview of favorite overview of favorite overview of favorite overview of favorite")
    )

    FavoriteScreenContent(
        innerPadding = PaddingValues(0.dp),
        dummyFavorite
    ){}
}