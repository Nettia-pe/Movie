package pe.nettia.movie.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pe.nettia.movie.ui.components.MovieGridItem
import pe.nettia.movie.ui.theme.MovieTheme
import pe.nettia.movie.ui.viewmodel.MovieViewModel
import androidx.compose.foundation.layout.Box

fun getPosterUrl(path: String?): String? =
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
    val loadingNextPage = viewModel.loadingNextPage.collectAsState().value
    val favoriteMovies = viewModel.favoriteMovies.collectAsState().value
    val gridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()

    // Detectar scroll al final para paginación
    LaunchedEffect(gridState) {
        snapshotFlow { gridState.layoutInfo }
            .collect { layoutInfo ->
                val lastVisible = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                if (lastVisible >= movies.size - 3 && !loadingNextPage) {
                    viewModel.loadNextPage()
                }
            }
    }

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
                state = gridState,
                columns = GridCells.Adaptive(minSize = 140.dp),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(movies) { movie ->
                    val isFavorite = favoriteMovies.any { it.id == movie.id }
                    Box {
                        MovieGridItem(
                            imageUrl = getPosterUrl(movie.posterUrl),
                            modifier = Modifier
                                .clickable { onMovieClick(movie.id) }
                        )
                        IconButton(
                            onClick = {
                                if (isFavorite) viewModel.removeFavorite(movie.id)
                                else viewModel.addFavorite(movie)
                            },
                            modifier = Modifier.align(Alignment.TopEnd)
                        ) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = if (isFavorite) "Quitar de favoritos" else "Agregar a favoritos",
                                tint = if (isFavorite) Color.Red else Color.Gray
                            )
                        }
                    }
                }
                if (loadingNextPage) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        androidx.compose.foundation.layout.Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
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