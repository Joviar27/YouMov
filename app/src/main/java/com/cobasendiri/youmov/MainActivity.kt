package com.cobasendiri.youmov

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.cobasendiri.youmov.ui.nav.Screen
import com.cobasendiri.youmov.ui.screen.detail.DetailScreen
import com.cobasendiri.youmov.ui.screen.favorite.FavoriteScreen
import com.cobasendiri.youmov.ui.screen.home.HomeScreen
import com.cobasendiri.youmov.ui.theme.YouMovTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val rootNavController = rememberNavController()

            YouMovTheme {
                NavHost(
                    navController = rootNavController,
                    startDestination = Screen.Home
                ){
                    composable<Screen.Home>{
                        HomeScreen(
                            onNavigateToDetail = {
                                rootNavController.navigate(Screen.Detail(it))
                            },
                            onNavigateToFavorite = {
                                rootNavController.navigate(Screen.Favorite)
                            }
                        )
                    }
                    composable<Screen.Detail> { backStackEntry ->
                        val args = backStackEntry.toRoute<Screen.Detail>()
                        DetailScreen(movieId = args.movieId) {
                            rootNavController.popBackStack()
                        }
                    }
                    composable<Screen.Favorite> {
                        FavoriteScreen(
                            onMovieClicked = {
                                rootNavController.navigate(Screen.Detail(it))
                            },
                            onNavigateBack = {
                                rootNavController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}