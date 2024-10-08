package app;

import java.util.List;
import java.util.Scanner;

public class Main {
    private final static Scanner sc = new Scanner(System.in);

    private static void listBooks(BookDAO bookDAO) {
        try {
            List<Book> books = bookDAO.listBooks();
            if (books.isEmpty()) {
                System.out.println("Nenhum livro encontrado.");
            } else {
                for (Book book : books) {
                    System.out.println(book);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar livros: " + e.getMessage());
        }
    }

    private static void insertBook(BookDAO bookDAO) {
        try {
            System.out.print("Título: ");
            String title = sc.nextLine();

            System.out.print("Autor: ");
            String author = sc.nextLine();

            System.out.print("Preço: ");
            double price = sc.nextDouble();

            System.out.print("Quantidade: ");
            int quantity = sc.nextInt();

            Book book = new Book(title, author, price, quantity);
            boolean isInserted = bookDAO.insertBook(book);

            if (isInserted) {
                System.out.println("Livro inserido com sucesso.");
            } else {
                System.out.println("Falha ao inserir o livro.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao inserir livro: " + e.getMessage());
        }
    }

    private static void deleteBook(BookDAO bookDAO) {
        try {
            System.out.print("ID do livro a ser excluído: ");
            int id = sc.nextInt();
            boolean bookIsDeleted = bookDAO.deleteBook(id);

            if (bookIsDeleted) {
                System.out.println("Livro excluído com sucesso.");
            } else {
                System.out.println("Nenhum livro foi deletado, verifique o ID digitado.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao excluir livro: " + e.getMessage());
        }
    }

    private static void updateBook(BookDAO bookDAO) {
        try {
            System.out.print("ID do livro a ser atualizado: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Novo Título: ");
            String title = sc.nextLine();

            System.out.print("Novo Autor: ");
            String author = sc.nextLine();

            System.out.print("Novo Preço: ");
            double price = sc.nextDouble();

            System.out.print("Nova Quantidade: ");
            int quantity = sc.nextInt();

            Book book = new Book(id, title, author, price, quantity);
            boolean isUpdated = bookDAO.updateBook(book);

            if (isUpdated) {
                System.out.println("Livro atualizado com sucesso.");
            } else {
                System.out.println("Falha ao atualizar o livro. Verifique o ID e tente novamente.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao atualizar livro: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        BookDAO bookDAO = new BookDAO();
        int option;

        do {
            System.out.println("\n--- Menu ---\n");
            System.out.println("1 - Listar Livros");
            System.out.println("2 - Inserir Livro");
            System.out.println("3 - Excluir Livro");
            System.out.println("4 - Atualizar Livro");
            System.out.println("5 - Sair");

            System.out.print("Escolha uma das opções: ");
            option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    listBooks(bookDAO);
                    break;
                case 2:
                    insertBook(bookDAO);
                    break;
                case 3:
                    deleteBook(bookDAO);
                    break;
                case 4:
                    updateBook(bookDAO);
                    break;
                case 5:
                    System.out.println("Saindo do programa.....");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (option != 5);

        sc.close();
        System.out.println("Obrigado por usar o programa! Volte sempre que necessário");
    }
}
