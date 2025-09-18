package com.example.bookstore.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bookstore.data.CarrinhoRepository
import com.example.bookstore.data.Livro
import com.example.bookstore.data.LivroRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen(navController: NavController) {
  val context = LocalContext.current
  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.MenuBook,
              contentDescription = "Ícone de livro"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Bookstore")
          }
        },
        actions = {
          IconButton(onClick = { navController.navigate("carrinho") }) {
            Icon(Icons.Default.ShoppingCart, contentDescription = "Carrinho")
          }
        }
      )
    }
  ) { innerPadding ->
    LazyVerticalGrid(
      columns = GridCells.Fixed(2),
      modifier = Modifier.padding(innerPadding),
      contentPadding = PaddingValues(16.dp),
      horizontalArrangement = Arrangement.spacedBy(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      items(LivroRepository.getCatalogo()) { livro ->
        LivroCard(
          livro = livro,
          onCardClick = { navController.navigate("detalhes/${livro.id}") },
          onAddToCartClick = {
            CarrinhoRepository.addItem(livro)
            Toast.makeText(context, "${livro.titulo} adicionado!", Toast.LENGTH_SHORT).show()
          }
        )
      }
    }
  }
}

@Composable
fun LivroCard(livro: Livro, onCardClick: () -> Unit, onAddToCartClick: () -> Unit) {
  Card(
    modifier = Modifier.clickable(onClick = onCardClick),
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
  ) {
    Column {
      Image(
        painter = painterResource(id = livro.capaDrawableRes),
        contentDescription = "Capa do livro ${livro.titulo}",
        modifier = Modifier.fillMaxWidth().aspectRatio(2f / 3f),
        contentScale = ContentScale.Crop
      )
      Column(
        modifier = Modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
      ) {
        Text(livro.titulo, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(livro.autor, fontSize = 14.sp, color = Color.Gray, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.Star, contentDescription = "Avaliação", tint = Color(0xFFFFC107), modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text(livro.avaliacao.toString(), fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
        Text("R$ ${"%.2f".format(livro.preco)}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onAddToCartClick, modifier = Modifier.fillMaxWidth()) { Text("Adicionar") }
      }
    }
  }
}
