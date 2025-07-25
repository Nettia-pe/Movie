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
import pe.nettia.movie.ui.navigation.AppNavHost

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieTheme {
                AppNavHost()
            }
        }
    }
}
