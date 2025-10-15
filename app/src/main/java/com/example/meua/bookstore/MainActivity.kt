package com.example.meua

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bookstore.ui.screens.CarrinhoScreen
import com.example.bookstore.ui.screens.CheckoutScreen
import com.example.bookstore.ui.screens.DetalhesScreen
import com.example.meua.bookstore.ui.screens.*
import com.example.meua.ui.theme.BookstoreTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      BookstoreTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
        ) {
          BookstoreApp()
        }
      }
    }
  }
}

@Composable
fun BookstoreApp() {
  val navController = rememberNavController()
  NavHost(navController = navController, startDestination = "catalogo") {


    composable("catalogo") {
      CatalogoScreen(navController = navController)
    }


    composable(
      route = "detalhes/{livroId}",
      arguments = listOf(navArgument("livroId") { type = NavType.IntType })
    ) { backStackEntry ->
      val livroId = backStackEntry.arguments?.getInt("livroId") ?: -1
      DetalhesScreen(navController = navController, livroId = livroId)
    }


    composable("carrinho") {
      CarrinhoScreen(navController = navController)
    }


    composable("checkout") {
      CheckoutScreen(navController = navController)
    }


    composable("admin") {
      AdminScreen(navController = navController)
    }

    composable("admin_autores") {
      AdminAutoresScreen(navController = navController)
    }
  }
}
