package com.example.bookstore.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.example.meua.bookstore.data.CarrinhoRepository
import com.example.meua.bookstore.data.ItemCarrinho


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarrinhoScreen(navController: NavController) {
  Scaffold(topBar = { TopAppBar(title = { Text("Carrinho de Compras") }, navigationIcon = { IconButton(onClick = { navController.popBackStack() }) {
    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
  } })}) { innerPadding ->
    if (CarrinhoRepository.itens.isEmpty()) { Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) { Text("Seu carrinho está vazio.") } }
    else {
      Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
        LazyColumn(modifier = Modifier.weight(1f), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
          items(CarrinhoRepository.itens) { item ->
            ItemCarrinhoCard(item = item, onQuantityChange = { novaQtd -> CarrinhoRepository.changeQuantity(item, novaQtd) }, onRemoveClick = { CarrinhoRepository.removeItem(item) })
          }
        }
        Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)) {
          Column(modifier = Modifier.padding(16.dp)) {
            Text("Total: R$ ${"%.2f".format(CarrinhoRepository.getTotal())}", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate("checkout") }, modifier = Modifier.fillMaxWidth().height(50.dp)) { Text("FINALIZAR COMPRA") }
          }
        }
      }
    }
  }
}

@Composable
fun ItemCarrinhoCard(item: ItemCarrinho, onQuantityChange: (Int) -> Unit, onRemoveClick: () -> Unit) {
  Card {
    Row(modifier = Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
      Image(painter = painterResource(id = item.livro.capaDrawableRes), contentDescription = item.livro.titulo, modifier = Modifier.size(100.dp).clip(MaterialTheme.shapes.medium), contentScale = ContentScale.Crop)
      Spacer(modifier = Modifier.width(16.dp))
      Column(modifier = Modifier.weight(1f)) {
        Text(item.livro.titulo, fontWeight = FontWeight.Bold)
        Text("R$ ${"%.2f".format(item.livro.preco)}", color = MaterialTheme.colorScheme.primary)
        Row(verticalAlignment = Alignment.CenterVertically) {
          IconButton(onClick = { onQuantityChange(item.quantidade - 1) }) { Icon(Icons.Default.Remove, contentDescription = "Remover um") }
          Text(item.quantidade.toString(), fontWeight = FontWeight.Bold)
          IconButton(onClick = { onQuantityChange(item.quantidade + 1) }) { Icon(Icons.Default.Add, contentDescription = "Adicionar um") }
        }
      }
      IconButton(onClick = onRemoveClick) { Icon(Icons.Default.Delete, contentDescription = "Remover Item") }
    }
  }
}
