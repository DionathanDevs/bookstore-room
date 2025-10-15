package com.example.meua.bookstore.data

import androidx.room.*

@Dao
interface LivroDAO {
  @Insert
  suspend fun inserir(livro: Livro)

  @Update
  suspend fun atualizar(livro: Livro)

  @Delete
  suspend fun deletar(livro: Livro)

  @Query("SELECT * FROM livros ORDER BY titulo ASC")
  suspend fun buscarTodos(): List<Livro>

  @Query("SELECT * FROM livros WHERE id = :id")
  suspend fun buscarPorId(id: Int): Livro?
}
