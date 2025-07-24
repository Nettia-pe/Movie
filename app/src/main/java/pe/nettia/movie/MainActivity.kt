package pe.nettia.movie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import pe.nettia.movie.ui.screens.MovieGridScreen
import pe.nettia.movie.ui.screens.MovieDetailScreen
import pe.nettia.movie.ui.theme.MovieTheme
import pe.nettia.movie.ui.viewmodel.MovieViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import pe.nettia.movie.ui.viewmodel.MovieDetailViewModel
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val movieViewModel: MovieViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "movies",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("movies") {
                            MovieGridScreen(
                                viewModel = movieViewModel,
                                onMovieClick = { movieId ->
                                    navController.navigate("detail/$movieId")
                                }
                            )
                        }
                        composable(
                            route = "detail/{movieId}",
                            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
                            val detailViewModel: MovieDetailViewModel = hiltViewModel()
                            detailViewModel.loadMovieDetail(movieId)
                            MovieDetailScreen(
                                viewModel = detailViewModel,
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
        movieViewModel.loadPopularMovies()
    }
}
