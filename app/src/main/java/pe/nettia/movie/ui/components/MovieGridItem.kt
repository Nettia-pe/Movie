package pe.nettia.movie.ui.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.*
import pe.nettia.movie.ui.theme.MovieTheme

@Composable
fun MovieGridItem(imageUrl: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        val painter = rememberAsyncImagePainter(model = imageUrl)
        val state = painter.state
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f/3f),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is AsyncImagePainter.State.Loading -> {
                    CircularProgressIndicator()
                }
                is AsyncImagePainter.State.Error -> {
                    Text("Error", color = Color.Red)
                }
                else -> {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Movie poster",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth().aspectRatio(2f/3f)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieGridItemPreview() {
    MovieTheme {
        MovieGridItem(imageUrl = "https://picsum.photos/300/450?random=1")
    }
} 