package com.example.bookstore.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bookstore.data.CarrinhoRepository
import com.example.bookstore.data.LivroRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalhesScreen(navController: NavController, livroId: Int) {
  val livro = LivroRepository.getLivroById(livroId)
  val context = LocalContext.current

  if (livro == null) {
    Scaffold { padding ->
      Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
        Text("Livro não encontrado.")
      }
    }
    return
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text(livro.titulo, maxLines = 1, overflow = TextOverflow.Ellipsis) },
        navigationIcon = {
          IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
          }
        }
      )
    }
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .verticalScroll(rememberScrollState())
    ) {
      Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
        Image(
          painter = painterResource(id = livro.capaDrawableRes),
          contentDescription = null,
          modifier = Modifier.fillMaxSize(),
          contentScale = ContentScale.Crop
        )
      }
      Column(modifier = Modifier.padding(16.dp)) {
        Text(livro.titulo, style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(livro.autor, style = MaterialTheme.typography.titleMedium, color = Color.Gray)
        Spacer(modifier = Modifier.height(16.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceAround,
          verticalAlignment = Alignment.CenterVertically
        ) {
          InfoDetalhe(icon = Icons.Default.Star, label = "Avaliação", value = "${livro.avaliacao} / 5.0")
          InfoDetalhe(icon = Icons.Default.MenuBook, label = "Gênero", value = livro.genero)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("R$ ${"%.2f".format(livro.preco)}", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)

        Divider(modifier = Modifier.padding(vertical = 16.dp))
        DetalheHeader(icon = Icons.Default.Info, text = "Sobre o Livro")
        Spacer(modifier = Modifier.height(8.dp))
   

        Text(livro.descricao, style = MaterialTheme.typography.bodyMedium, lineHeight = 24.sp)
        Spacer(modifier = Modifier.height(24.dp))
        Button(
          onClick = {
            CarrinhoRepository.addItem(livro)
            Toast.makeText(context, "Adicionado!", Toast.LENGTH_SHORT).show()
          },
          modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
          Text("ADICIONAR AO CARRINHO")
        }
      }
    }
  }
}

@Composable
fun InfoDetalhe(icon: ImageVector, label: String, value: String) {
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
    Spacer(modifier = Modifier.height(4.dp))
    Text(value, fontWeight = FontWeight.Bold)
    Text(label, fontSize = 12.sp, color = Color.Gray)
  }
}


@Composable
fun DetalheHeader(icon: ImageVector, text: String) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.fillMaxWidth()
  ) {
    Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
    Spacer(modifier = Modifier.width(8.dp))
    Text(text, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
  }
}
