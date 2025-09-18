# Bookstore App

Este é um projeto de estudo de um aplicativo móvel de e-commerce para uma livraria, desenvolvido nativamente em Kotlin. O aplicativo simula a experiência de um usuário navegando por um catálogo de livros, vendo detalhes, adicionando itens a um carrinho e finalizando a compra.

O objetivo principal deste projeto foi aplicar e demonstrar os conceitos fundamentais de desenvolvimento Android moderno, incluindo gerenciamento de estado, navegação entre telas e a criação de interfaces de usuário ricas e reativas.

## 📚 Funcionalidades

O aplicativo é composto por 4 telas principais, todas interligadas por um sistema de navegação:

* **Tela de Catálogo:**
    * Exibe os livros disponíveis em uma grade vertical rolável.
    * Cada livro é apresentado em um `Card` com imagem, título, autor, avaliação e preço.
    * Permite adicionar um livro diretamente ao carrinho.
    * Permite navegar para a tela de detalhes ao clicar em um livro.

* **Tela de Detalhes:**
    * Mostra informações detalhadas sobre um livro selecionado.
    * Apresenta uma imagem de capa, descrição completa e outros detalhes como gênero e avaliação.
    * Possui um botão para adicionar o item ao carrinho.

* **Tela do Carrinho de Compras:**
    * Lista todos os itens adicionados pelo usuário.
    * Permite alterar a quantidade de cada item (`+` e `-`) ou removê-lo.
    * Calcula e exibe o valor total da compra em tempo real.
    * Contém um botão para prosseguir para o checkout.

* **Tela de Checkout:**
    * Apresenta um resumo do pedido.
    * Contém um formulário simples (`TextField`) para o usuário inserir dados como nome e endereço.
    * Simula a finalização do pedido, limpando o carrinho e retornando à tela inicial.

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Kotlin
* **UI Toolkit:** Jetpack Compose
* **Navegação:** O componente Navigation oferece suporte para aplicativos Jetpack Compose.
* **Design:** Material Design 3
* **IDE:** Android Studio

## 🚀 Como Executar o Projeto

1.  **Clone o Repositório:**
    ```bash
    git clone [https://github.com/DionathanDevs/clima-tempo-kotlin.git](https://github.com/DionathanDevs/clima-tempo-kotlin.git)
    ```
2.  **Abra no Android Studio:**
    * Abra o Android Studio e selecione "Open".
    * Navegue até a pasta do projeto clonado e abra-a.

3.  **Adicione as Imagens (Passo Crucial):**
    * Navegue até a pasta `app/src/main/res/drawable`.
    * Adicione 5 arquivos de imagem com nomes em letras minúsculas (ex: `cover_duna.jpg`).

4.  **Sincronize o Gradle:**
    * O Android Studio deve sincronizar o projeto automaticamente. Caso contrário, clique em "Sync Now".

5.  **Execute o Aplicativo:**
    * Selecione um emulador ou conecte um dispositivo físico.
    * Clique no botão "Run 'app'" (▶️).


**Autor:** Dionathan Boing Mesquita e Eduardo de Paula Pupo
