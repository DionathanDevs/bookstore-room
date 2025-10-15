package com.example.meua.bookstore.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

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
        _itens[index] = item.copy(quantidade = novaQuantidade)
      }
    }
  }

  fun clearCart() {
    _itens.clear()
  }

  fun getTotal(): Double = _itens.sumOf { it.livro.preco * it.quantidade }
}
