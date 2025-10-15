package com.example.meua.bookstore.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.meua.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Livro::class, Autor::class], version = 2)
abstract class BookstoreDatabase : RoomDatabase() {

  abstract fun livroDAO(): LivroDAO
  abstract fun autorDAO(): AutorDAO

  companion object {
    @Volatile
    private var INSTANCE: BookstoreDatabase? = null

    fun getDatabase(context: Context): BookstoreDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          BookstoreDatabase::class.java,
          "bookstore_database"
        )
          // --- A CORREÇÃO ESTÁ AQUI ---
          // Adiciona a instrução para recriar o banco se a versão mudar.
          .fallbackToDestructiveMigration()
          .addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
              super.onCreate(db)
              CoroutineScope(Dispatchers.IO).launch {
                prePopulateDatabase(getDatabase(context))
              }
            }
          })
          .build()
        INSTANCE = instance
        instance
      }
    }

    suspend fun prePopulateDatabase(database: BookstoreDatabase) {
      val livroDAO = database.livroDAO()
      val autorDAO = database.autorDAO()

      autorDAO.inserir(Autor(id = 1, nome = "J.R.R. Tolkien", biografia = "Autor de O Senhor dos Anéis."))
      autorDAO.inserir(Autor(id = 2, nome = "Douglas Adams", biografia = "Autor de O Guia do Mochileiro das Galáxias."))
      autorDAO.inserir(Autor(id = 3, nome = "Frank Herbert", biografia = "Autor de Duna."))

      val livrosIniciais = listOf(
        Livro(1, "O Senhor dos Anéis", "J.R.R. Tolkien", R.drawable.senhor_aneis, 75.50, "A conclusão épica da jornada de Frodo para destruir o Um Anel.", "Fantasia", 4.9),
        Livro(2, "O Guia do Mochileiro das Galáxias", "Douglas Adams", R.drawable.galaxia, 42.00, "Uma comédia de ficção científica sobre a busca pelo sentido da vida.", "Ficção Científica", 4.8),
        Livro(3, "Duna", "Frank Herbert", R.drawable.duna, 89.90, "Em um futuro distante, casas nobres lutam pelo controle do planeta deserto Arrakis.", "Ficção Científica", 4.7)
      )
      livrosIniciais.forEach { livroDAO.inserir(it) }
    }
  }
}
