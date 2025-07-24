package pe.nettia.movie.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pe.nettia.movie.ui.components.MovieGridItem
import pe.nettia.movie.ui.theme.MovieTheme
import pe.nettia.movie.ui.viewmodel.MovieViewModel

private fun getPosterUrl(path: String?): String? =
    path?.let { "https://image.tmdb.org/t/p/w500$it" }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieGridScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieViewModel,
    onMovieClick: (Int) -> Unit
) {
    val movies = viewModel.movies.collectAsState().value
    val error = viewModel.error.collectAsState().value
    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        if (error != null) {
            androidx.compose.foundation.layout.Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = error, color = Color.Red)
                    Button(onClick = { viewModel.loadPopularMovies() }) {
                        Text("Reintentar")
                    }
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 140.dp),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(movies) { movie ->
                    MovieGridItem(
                        imageUrl = getPosterUrl(movie.posterUrl),
                        modifier = Modifier.clickable { onMovieClick(movie.id) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieGridScreenPreview() {
    MovieTheme {
        // Preview sin ViewModel real
    }
} 