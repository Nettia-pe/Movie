package pe.nettia.movie.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.*
import pe.nettia.movie.ui.components.MovieGridItem
import pe.nettia.movie.ui.theme.MovieTheme

private val sampleImages = List(20) { "https://placekitten.com/300/450?image=$it" }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieGridScreen(modifier: Modifier = Modifier) {
    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 140.dp),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(sampleImages) { imageUrl ->
                MovieGridItem(imageUrl = imageUrl)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieGridScreenPreview() {
    MovieTheme {
        MovieGridScreen()
    }
} 