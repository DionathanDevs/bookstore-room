package com.example.meua.bookstore.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.meua.bookstore.data.Autor
import com.example.meua.bookstore.data.BookstoreDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminAutoresScreen(navController: NavController) {
  var nome by remember { mutableStateOf("") }
  var biografia by remember { mutableStateOf("") }
  var listaAutores by remember { mutableStateOf<List<Autor>>(emptyList()) }
  var autorEmEdicao by remember { mutableStateOf<Autor?>(null) }

  val context = LocalContext.current
  val autorDAO = BookstoreDatabase.getDatabase(context).autorDAO()

  fun atualizarLista() {
    CoroutineScope(Dispatchers.IO).launch {
      listaAutores = autorDAO.buscarTodos()
    }
  }

  LaunchedEffect(Unit) {
    atualizarLista()
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Gerenciar Autores") },
        navigationIcon = {
          IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
          }
        }
      )
    }
  ) { innerPadding ->
    Column(
      modifier = Modifier.padding(innerPadding).padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      TextField(value = nome, onValueChange = { nome = it }, label = { Text("Nome do Autor") }, modifier = Modifier.fillMaxWidth())
      TextField(value = biografia, onValueChange = { biografia = it }, label = { Text("Biografia") }, modifier = Modifier.fillMaxWidth())

      Button(
        onClick = {
          if (nome.isNotBlank() && biografia.isNotBlank()) {
            CoroutineScope(Dispatchers.IO).launch {
              if (autorEmEdicao == null) {
                autorDAO.inserir(Autor(nome = nome, biografia = biografia))
              } else {
                autorDAO.atualizar(autorEmEdicao!!.copy(nome = nome, biografia = biografia))
                autorEmEdicao = null
              }
              atualizarLista()
              nome = ""
              biografia = ""
            }
          }
        },
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(text = if (autorEmEdicao == null) "Adicionar Autor" else "Atualizar Autor")
      }

      LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
        items(listaAutores) { autor ->
          Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Text(text = autor.nome, modifier = Modifier.weight(1f))
            Button(onClick = {
              autorEmEdicao = autor
              nome = autor.nome
              biografia = autor.biografia
            }) { Text("Editar") }
            Button(onClick = {
              CoroutineScope(Dispatchers.IO).launch {
                autorDAO.deletar(autor)
                atualizarLista()
              }
            }) { Text("X") }
          }
        }
      }
    }
  }
}
