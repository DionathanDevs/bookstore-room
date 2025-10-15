package com.example.meua.bookstore.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "autores")
data class Autor(
  @PrimaryKey(autoGenerate = true)
  val id: Int = 0,
  val nome: String,
  val biografia: String
)
