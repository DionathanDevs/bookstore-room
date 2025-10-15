package com.example.meua.bookstore.data

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "livros")
data class Livro(
  @PrimaryKey(autoGenerate = true)
  val id: Int = 0,
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
  val quantidade: Int
)
