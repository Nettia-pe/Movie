package pe.nettia.movie.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import pe.nettia.movie.R
import pe.nettia.movie.domain.model.Movie
import pe.nettia.movie.ui.viewmodel.MovieDetailViewModel
import pe.nettia.movie.ui.viewmodel.MovieViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel,
    onBack: () -> Unit,
    movieViewModel: MovieViewModel? = null, // para favoritos
    movieFromList: Movie? = null // para obtener datos completos si no hay internet
) {
    val detail = viewModel.movieDetail.collectAsState().value
    val loading = viewModel.loading.collectAsState().value
    val error = viewModel.error.collectAsState().value
    val isFavorite = movieViewModel?.favoriteMovies?.collectAsState()?.value?.any { it.id == detail?.id } == true

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier.fillMaxSize()) {
            CenterAlignedTopAppBar(
                title = { Text("Detalle de Película") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                actions = {
                    if (detail != null && movieViewModel != null) {
                        IconButton(onClick = {
                            val movie = movieFromList ?: Movie(
                                id = detail.id,
                                title = detail.title,
                                posterUrl = detail.posterUrl,
                                overview = detail.overview ?: "",
                                releaseDate = detail.releaseDate ?: ""
                            )
                            if (isFavorite) movieViewModel.removeFavorite(movie.id)
                            else movieViewModel.addFavorite(movie)
                        }) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = if (isFavorite) "Quitar de favoritos" else "Agregar a favoritos",
                                tint = if (isFavorite) Color.Red else Color.Gray
                            )
                        }
                    }
                }
            )
            when {
                loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                error != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = error, color = Color.Red)
                    }
                }
                detail != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        detail.backdropUrl?.let {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(it)
                                    .diskCachePolicy(CachePolicy.ENABLED)
                                    .networkCachePolicy(CachePolicy.ENABLED)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                placeholder = painterResource(id = R.drawable.ic_placeholder),
                                error = painterResource(id = R.drawable.ic_broken_image)
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(detail.title, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("${detail.releaseDate ?: ""}  •  ${detail.runtime ?: ""} min", style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        if (detail.genres.isNotEmpty()) {
                            Text(detail.genres.joinToString(", "), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(detail.overview ?: "Sin descripción", style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.height(24.dp))
                        Text("Reparto", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
                            items(detail.cast) { actor ->
                                Column(
                                    modifier = Modifier
                                        .width(80.dp)
                                        .padding(end = 8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(actor.profileUrl)
                                            .diskCachePolicy(CachePolicy.ENABLED)
                                            .networkCachePolicy(CachePolicy.ENABLED)
                                            .crossfade(true)
                                            .build(),
                                        contentDescription = actor.name,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(64.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.surfaceVariant),
                                        placeholder = painterResource(id = R.drawable.ic_placeholder),
                                        error = painterResource(id = R.drawable.ic_broken_image)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        actor.name,
                                        style = MaterialTheme.typography.bodySmall,
                                        maxLines = 1,
                                        modifier = Modifier.align(Alignment.CenterHorizontally)
                                    )
                                    actor.character?.let {
                                        Text(
                                            it,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = Color.Gray,
                                            maxLines = 1,
                                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
} 