package pe.nettia.movie.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import pe.nettia.movie.ui.components.MovieGridItem
import pe.nettia.movie.ui.viewmodel.FavoritesViewModel
import androidx.compose.ui.unit.dp

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    onMovieClick: (pe.nettia.movie.domain.model.Movie) -> Unit
) {
    val favoriteMovies = viewModel.favoriteMovies.collectAsState().value
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        if (favoriteMovies.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No tienes favoritos aún")
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 140.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(8.dp)
            ) {
                items(favoriteMovies) { movie ->
                    Box {
                        MovieGridItem(
                            imageUrl = getPosterUrl(movie.posterUrl),
                            modifier = Modifier.clickable { onMovieClick(movie) }
                        )
                        IconButton(
                            onClick = { viewModel.removeFavorite(movie.id) },
                            modifier = Modifier.align(Alignment.TopEnd)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = "Quitar de favoritos",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
        }
    }
} 