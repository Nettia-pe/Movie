package pe.nettia.movie.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pe.nettia.movie.ui.screens.MovieGridScreen
import pe.nettia.movie.ui.screens.MovieDetailScreen
import pe.nettia.movie.ui.screens.FavoritesScreen
import pe.nettia.movie.ui.screens.SettingsScreen
import pe.nettia.movie.ui.viewmodel.MovieViewModel
import pe.nettia.movie.ui.viewmodel.MovieDetailViewModel
import androidx.hilt.navigation.compose.hiltViewModel

sealed class BottomNavItem(val route: String, val icon: androidx.compose.ui.graphics.vector.ImageVector, val label: String) {
    object Movies : BottomNavItem("movies", Icons.Filled.Home, "Películas")
    object Favorites : BottomNavItem("favorites", Icons.Filled.Favorite, "Favoritos")
    object Settings : BottomNavItem("settings", Icons.Filled.Settings, "Configuración")
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val bottomNavItems = listOf(BottomNavItem.Movies, BottomNavItem.Favorites, BottomNavItem.Settings)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = currentRoute in bottomNavItems.map { it.route }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            NavHost(
                navController = navController,
                startDestination = BottomNavItem.Movies.route
            ) {
                composable(BottomNavItem.Movies.route) {
                    val movieViewModel: MovieViewModel = hiltViewModel()
                    MovieGridScreen(
                        viewModel = movieViewModel,
                        onMovieClick = { movieId ->
                            navController.navigate("detail/$movieId")
                        }
                    )
                }
                composable(BottomNavItem.Favorites.route) {
                    FavoritesScreen()
                }
                composable(BottomNavItem.Settings.route) {
                    SettingsScreen()
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