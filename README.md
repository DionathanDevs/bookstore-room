# Bookstore App - Projeto de Estudo com Room e Compose

Este é um projeto de estudo de um aplicativo móvel para uma livraria, desenvolvido nativamente em Kotlin utilizando Jetpack Compose. O aplicativo permite visualizar um catálogo de livros, ver detalhes, adicionar itens a um carrinho de compras, e gerenciar o catálogo de livros e autores através de operações CRUD (Criar, Ler, Atualizar, Deletar) com persistência de dados local.

O objetivo principal deste projeto foi aplicar os conceitos de desenvolvimento Android moderno, incluindo a criação de interfaces reativas, gerenciamento de estado, navegação e persistência de dados com a biblioteca Room.

## 📚 Funcionalidades

O aplicativo é composto por 6 telas principais, todas interligadas por um sistema de navegação:

* **Tela de Catálogo:** Exibe os livros disponíveis em uma grade, lendo os dados diretamente do banco de dados.
* **Tela de Detalhes:** Mostra informações completas sobre um livro específico, também buscando os dados do banco.
* **Tela do Carrinho de Compras:** Gerencia em memória os itens que o usuário adiciona.
* **Tela de Checkout:** Simula um formulário de finalização de compra.
* **Tela de Gerenciamento de Livros (CRUD 1):** Permite criar, ler, atualizar e deletar livros no banco de dados.
* **Tela de Gerenciamento de Autores (CRUD 2):** Permite criar, ler, atualizar e deletar autores no banco de dados.

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Kotlin
* **UI Toolkit:** Jetpack Compose
* **Persistência de Dados:** Room Database. [cite_start]O Room é uma biblioteca de persistência que atua como uma camada de abstração sobre o SQLite[cite: 166], simplificando o acesso ao banco de dados. Ele é composto por três componentes principais:
    * [cite_start]**Entity (Entidade):** Representa uma tabela no banco de dados[cite: 171].
    * [cite_start]**DAO (Data Access Object):** Uma interface que define as operações de banco de dados[cite: 172].
    * [cite_start]**Room Database:** O gerenciador que fornece acesso à instância do banco de dados[cite: 173].
* [cite_start]**Concorrência:** Kotlin Coroutines para operações de banco de dados assíncronas [cite: 175][cite_start], garantindo que a UI não trave[cite: 175].
* **Navegação:** Navigation Compose.
* **Design:** Material Design 3.
* **Injeção de Dependência (KSP):** Usado pelo Room para gerar o código necessário em tempo de compilação.

## 🚀 Como Executar o Projeto

1.  **Clone o Repositório.**
2.  **Abra no Android Studio.**
3.  **Adicione as Imagens:** Navegue até a pasta `app/src/main/res/drawable` e adicione os arquivos de imagem para as capas dos livros.
4.  **Sincronize o Gradle:** Clique em "Sync Now" para baixar todas as dependências.
5.  **Execute o Aplicativo:** Selecione um emulador ou dispositivo e clique em "Run 'app'" (▶️).

## ✅ Requisitos do Trabalho Implementados

Este projeto foi desenvolvido para atender a uma lista específica de requisitos de aprendizado:

* [x] **Mínimo de 4 telas:** Implementadas 6 telas (Catálogo, Detalhes, Carrinho, Checkout, AdminLivros, AdminAutores).
* [x] **Mínimo de 5 funções compostables:** Várias funções foram criadas, como `CatalogoScreen`, `DetalhesScreen`, `LivroCard`, `AdminScreen`, etc.
* [x] **Interação do front com o back:** As telas de gerenciamento realizam operações CRUD no banco de dados Room. As telas de visualização leem os dados do banco.
* [x] **`TextField`**: Utilizado nas telas de gerenciamento (`AdminScreen`, `AdminAutoresScreen`) para inserir e editar dados.
* [x] **`Button`**: Utilizado em todas as telas para ações como "Adicionar", "Atualizar", "Deletar", etc.
* [x] **`Remember`** e **`mutableStateOf`**: Usados nas telas de gerenciamento para controlar o estado dos campos de texto e da lista de itens.
* [x] **`Card`**: Usado para exibir os livros no catálogo e os itens no carrinho.
* [x] **`Surface`**: Usado como container principal do aplicativo.
* [x] **`Scaffold`**: Usado para a estrutura básica de todas as telas.
* [x] **`Row`, `Column`, `Box`**: Usados extensivamente para criar os layouts.
* [x] **`Alinhamentos e Espaçamentos`**: Aplicados em todo o projeto.
* [cite_start][x] **`class / dataclass`**: `data class Livro` e `data class Autor` são usadas como `@Entity` para o Room[cite: 171]. `data class ItemCarrinho` é usada para o estado do carrinho.
* [x] **`Toast Message`**: Usado para dar feedback ao usuário.
* [x] **`Navigation`**: O `NavHost` gerencia as 6 rotas, com `startDestination`. A navegação é feita com `navController.navigate()` e `navController.popBackStack()`. A passagem de argumentos é usada para a tela de detalhes.
* [x] **`LazyList/Column`**: `LazyVerticalGrid` é usado no catálogo e `LazyColumn` nas telas de gerenciamento e no carrinho.
* [x] **`lambdas`**: Utilizadas em todos os eventos de clique (`onClick`).
* [cite_start][x] **Banco de Dados (CRUD):** As operações de banco de dados são definidas no `DAO` com funções `suspend` [cite: 176] [cite_start]e chamadas dentro de um `CoroutineScope(Dispatchers.IO)`[cite: 208], como demonstrado no material de aula. [cite_start]A busca inicial de dados é feita de forma assíncrona usando `LaunchedEffect`[cite: 205].

---

**Autor:** Dionathan Boing Mesquita
