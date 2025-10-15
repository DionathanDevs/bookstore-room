package com.example.meua.bookstore.data


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AutorDAO {
  @Insert
  suspend fun inserir(autor: Autor)

  @Update
  suspend fun atualizar(autor: Autor)

  @Delete
  suspend fun deletar(autor: Autor)

  @Query("SELECT * FROM autores ORDER BY nome ASC")
  suspend fun buscarTodos(): List<Autor>
}
