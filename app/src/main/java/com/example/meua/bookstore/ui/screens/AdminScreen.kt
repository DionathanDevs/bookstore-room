package com.example.meua.bookstore.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
// --- NOVOS IMPORTS NECESSÁRIOS ---
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
// --- FIM DOS NOVOS IMPORTS ---
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.meua.R
import com.example.meua.bookstore.data.BookstoreDatabase
import com.example.meua.bookstore.data.Livro
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(navController: NavController) {
  var titulo by remember { mutableStateOf("") }
  var autor by remember { mutableStateOf("") }
  var preco by remember { mutableStateOf("") }
  var listaLivros by remember { mutableStateOf<List<Livro>>(emptyList()) }
  var livroEmEdicao by remember { mutableStateOf<Livro?>(null) }
  val context = LocalContext.current
  val livroDAO = BookstoreDatabase.getDatabase(context).livroDAO()

  fun atualizarLista() {
    CoroutineScope(Dispatchers.IO).launch {
      listaLivros = livroDAO.buscarTodos()
    }
  }

  LaunchedEffect(Unit) {
    atualizarLista()
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Gerenciar Catálogo") },
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
        .padding(innerPadding)
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Button(
        onClick = { navController.navigate("admin_autores") },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
      ) {
        Text("Gerenciar Autores")
      }

      Divider(modifier = Modifier.padding(vertical = 16.dp))

      TextField(value = titulo, onValueChange = { titulo = it }, label = { Text("Título") }, modifier = Modifier.fillMaxWidth())
      TextField(value = autor, onValueChange = { autor = it }, label = { Text("Autor") }, modifier = Modifier.fillMaxWidth())


      TextField(
        value = preco,
        onValueChange = { novoValor ->

          if (novoValor.matches(Regex("^\\d*\\.?\\d*\$"))) {
            preco = novoValor
          }
        },
        label = { Text("Preço") },
        modifier = Modifier.fillMaxWidth(),

        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
      )


      Button(
        onClick = {

          val precoDouble = preco.toDoubleOrNull()
          if (titulo.isNotBlank() && autor.isNotBlank() && precoDouble != null) {
            CoroutineScope(Dispatchers.IO).launch {
              if (livroEmEdicao == null) {
                livroDAO.inserir(Livro(titulo = titulo, autor = autor, preco = precoDouble, capaDrawableRes = R.drawable.duna, descricao = "Descrição padrão.", genero = "Gênero", avaliacao = 4.5))
              } else {
                livroDAO.atualizar(livroEmEdicao!!.copy(titulo = titulo, autor = autor, preco = precoDouble))
                livroEmEdicao = null
              }
              atualizarLista()
              titulo = ""
              autor = ""
              preco = ""
            }
          }
        },
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(text = if (livroEmEdicao == null) "Adicionar Livro" else "Atualizar Livro")
      }

      LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
        items(listaLivros) { livro ->
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Text(text = livro.titulo, modifier = Modifier.weight(1f))
            Button(onClick = {
              livroEmEdicao = livro
              titulo = livro.titulo
              autor = livro.autor
              preco = livro.preco.toString()
            }) { Text("Editar") }
            Button(onClick = {
              CoroutineScope(Dispatchers.IO).launch {
                livroDAO.deletar(livro)
                atualizarLista()
              }
            }) { Text("X") }
          }
        }
      }
    }
  }
}
