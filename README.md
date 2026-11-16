# 📚 Gerenciador de Biblioteca

**Um sistema desktop desenvolvido em Java para facilitar a administração de bibliotecas acadêmicas, permitindo organizar o acervo de livros, gerenciar empréstimos e acompanhar as informações da biblioteca por meio de uma interface gráfica intuitiva.**

O **Gerenciador de Biblioteca** foi desenvolvido com o objetivo de simplificar as atividades de gerenciamento de uma biblioteca, centralizando operações essenciais como cadastro, consulta, edição e exclusão de livros, além do controle de empréstimos e da geração de relatórios.

A aplicação busca tornar a administração do acervo mais organizada e eficiente, reduzindo a necessidade de controles manuais e facilitando o acesso às informações dos livros e empréstimos.

## 🎯 Funcionalidades

- **Cadastro de livros:** inclusão de novos exemplares no acervo da biblioteca.
- **Listagem de livros:** visualização dos livros cadastrados.
- **Pesquisa de livros:** localização de livros no acervo.
- **Edição de livros:** atualização das informações dos livros cadastrados.
- **Exclusão de livros:** remoção de registros do acervo.
- **Controle de empréstimos:** gerenciamento dos empréstimos realizados pela biblioteca.
- **Relatórios:** visualização de informações gerais sobre a biblioteca.

## 🛠️ Tecnologias utilizadas

O projeto foi desenvolvido utilizando as seguintes tecnologias:

- **Java (JDK 21):** linguagem utilizada na implementação das regras de negócio e funcionalidades.
- **Java Swing:** biblioteca utilizada para construção da interface gráfica desktop.

## 🏗️ Estrutura do projeto

O código está organizado em três pacotes principais, seguindo uma separação de responsabilidades entre interface, regras de negócio e representação dos dados.

```text
src/
├── controller/
│   └── BibliotecaService.java
├── model/
│   ├── Emprestimo.java
│   └── Livro.java
└── view/
    └── TelaPrincipal.java
```

### Organização dos pacotes

- **`model`:** contém as classes que representam as entidades do sistema, como livros e empréstimos.
- **`controller`:** concentra as regras de negócio e o gerenciamento das operações da biblioteca.
- **`view`:** contém a implementação da interface gráfica e das telas de interação com o usuário.

## 🎥 Demonstração do projeto

<img width="1920" height="1020" alt="image" src="https://github.com/user-attachments/assets/237242a4-b147-4ece-abb7-e8c54e793f01" />

<img width="1920" height="1020" alt="image" src="https://github.com/user-attachments/assets/9edf1de8-d057-4f48-b5fd-0ff30af947eb" />

<img width="1920" height="1020" alt="image" src="https://github.com/user-attachments/assets/c36391f8-c0bd-44c8-ba92-8a721a37347e" />

<img width="1920" height="1020" alt="image" src="https://github.com/user-attachments/assets/ef9663c1-2de9-4e98-a6a8-658d088257d1" />

Confira o vídeo de apresentação para conhecer o funcionamento da aplicação:

[▶️ Assistir à demonstração do Gerenciador de Biblioteca](https://www.youtube.com/watch?v=4kn6Gih6sG0)

## 👨‍🏫 Orientação acadêmica

**Professor orientador:** Hudson Neves Silva

## 👨‍💻 Equipe de desenvolvimento

- Daniel Guimarães Botelho
- Davi Miranda da Silva
- Enzo Gabriel Ferreira dos Santos
- Ian Brandão dos Santos Ribeiro
- José Gustavo de Queiroz Medeiros
- Vinícius Moreira de Carvalho

---

Desenvolvido como projeto acadêmico, com o propósito de aplicar conceitos de programação orientada a objetos, desenvolvimento de interfaces gráficas e organização de software utilizando Java.
