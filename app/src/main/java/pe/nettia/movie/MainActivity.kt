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
import dagger.hilt.android.AndroidEntryPoint
import pe.nettia.movie.ui.screens.MovieGridScreen
import pe.nettia.movie.ui.theme.MovieTheme
import pe.nettia.movie.ui.viewmodel.MovieViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val movieViewModel: MovieViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MovieGridScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = movieViewModel
                    )
                }
            }
        }
        movieViewModel.loadPopularMovies()
    }
}
