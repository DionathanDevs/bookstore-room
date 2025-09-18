package com.example.bookstore.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bookstore.data.CarrinhoRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(navController: NavController) {
  val context = LocalContext.current
  var nome by remember { mutableStateOf("") }
  var endereco by remember { mutableStateOf("") }
  var cartao by remember { mutableStateOf("") }

  Scaffold(topBar = { TopAppBar(title = { Text("Finalizar Compra") }, navigationIcon = { IconButton(onClick = { navController.popBackStack() }) {
    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
  } })}) { innerPadding ->
    Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp).verticalScroll(rememberScrollState())) {
      Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text("Resumo do Pedido", style = MaterialTheme.typography.titleLarge)
          Spacer(modifier = Modifier.height(8.dp))
          CarrinhoRepository.itens.forEach { item ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
              Text("${item.quantidade}x ${item.livro.titulo}")
              Text("R$ ${"%.2f".format(item.livro.preco * item.quantidade)}")
            }
          }
          Divider(modifier = Modifier.padding(vertical = 8.dp))
          Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Total", fontWeight = FontWeight.Bold)
            Text("R$ ${"%.2f".format(CarrinhoRepository.getTotal())}", fontWeight = FontWeight.Bold)
          }
        }
      }
      Spacer(modifier = Modifier.height(24.dp))
      Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Informações de Entrega e Pagamento", style = MaterialTheme.typography.titleLarge)
        OutlinedTextField(value = nome, onValueChange = { nome = it }, label = { Text("Nome Completo") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = endereco, onValueChange = { endereco = it }, label = { Text("Endereço") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = cartao, onValueChange = { cartao = it }, label = { Text("Número do Cartão") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
      }
      Spacer(modifier = Modifier.weight(1f))
      Button(onClick = {
        if (nome.isNotBlank() && endereco.isNotBlank() && cartao.isNotBlank()) {
          Toast.makeText(context, "Pedido realizado com sucesso!", Toast.LENGTH_LONG).show()
          CarrinhoRepository.clearCart()
          navController.navigate("catalogo") { popUpTo("catalogo") { inclusive = true } }
        } else {
          Toast.makeText(context, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
        }
      }, modifier = Modifier.fillMaxWidth().height(50.dp)) { Text("CONFIRMAR PEDIDO") }
    }
  }
}
