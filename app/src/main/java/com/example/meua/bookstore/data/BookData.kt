package com.example.bookstore.data

import androidx.annotation.DrawableRes
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.bookstore.R


data class Livro(
  val id: Int,
  val titulo: String,
  val autor: String,
  @DrawableRes val capaDrawableRes: Int,
  val preco: Double,
  val descricao: String,
  val genero: String,
  val avaliacao: Double
)


data class ItemCarrinho(
  val livro: Livro,
  var quantidade: Int
)


object CarrinhoRepository {
  private val _itens = mutableStateListOf<ItemCarrinho>()
  val itens: SnapshotStateList<ItemCarrinho> = _itens

  fun addItem(livro: Livro) {
    val itemExistente = _itens.find { it.livro.id == livro.id }
    if (itemExistente != null) {
      changeQuantity(itemExistente, itemExistente.quantidade + 1)
    } else {
      _itens.add(ItemCarrinho(livro = livro, quantidade = 1))
    }
  }

  fun removeItem(item: ItemCarrinho) {
    _itens.remove(item)
  }

  fun changeQuantity(item: ItemCarrinho, novaQuantidade: Int) {
    if (novaQuantidade <= 0) {
      removeItem(item)
    } else {
      val index = _itens.indexOf(item)
      if (index != -1) {
        val itemAtualizado = item.copy(quantidade = novaQuantidade)
        _itens[index] = itemAtualizado
      }
    }
  }

  fun clearCart() {
    _itens.clear()
  }

  fun getTotal(): Double {
    return _itens.sumOf { it.livro.preco * it.quantidade }
  }
}


object LivroRepository {
  private val livros = listOf(


    Livro(1, "O Senhor dos Anéis", "J.R.R. Tolkien", R.drawable.senhor_aneis, 75.50, "A conclusão épica da jornada de Frodo para destruir o Um Anel.", "Fantasia", 4.9),
    Livro(2, "O Guia do Mochileiro das Galáxias", "Douglas Adams", R.drawable.galaxia, 42.00, "Uma comédia de ficção científica sobre a busca pelo sentido da vida, o universo e tudo mais.", "Ficção Científica", 4.8),
    Livro(3, "Duna", "Frank Herbert", R.drawable.duna, 89.90, "Em um futuro distante, casas nobres lutam pelo controle do planeta deserto Arrakis.", "Ficção Científica", 4.7),


    Livro(
      id = 4,
      titulo = "Harry Potter e a Pedra Filosofal",
      autor = "J.K. Rowling",
      capaDrawableRes = R.drawable.harry,
      preco = 35.00,
      descricao = "Harry Potter descobre em seu aniversário de 11 anos que é um bruxo e é convidado para estudar na Escola de Magia e Bruxaria de Hogwarts.", // Sinopse corrigida
      genero = "Fantasia",
      avaliacao = 4.9
    ),


    Livro(
      id = 5,
      titulo = "Demon Slayer Vol. 1",
      autor = "Koyoharu Gotouge",
      capaDrawableRes = R.drawable.demon,
      preco = 65.00,
      descricao = "Tanjiro Kamado tem sua vida destruída quando um demônio massacra sua família. Ele parte em uma jornada para se tornar um caçador de demônios e curar sua irmã.", // Sinopse corrigida
      genero = "Mangá",
      avaliacao = 4.9
    )
  )

  fun getCatalogo() = livros
  fun getLivroById(id: Int) = livros.find { it.id == id }
}
