package br.com.estruturas.app;

import br.com.estruturas.avl.AVLTree;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // scaner para escolha
        AVLTree<Integer> tree = new AVLTree<Integer>(); // criação da nova arvore de integer
        int choice, value; //variaveis para escolha e inserção caso necessário

        do {
            System.out.println("\n--- AVL Tree Operations ---");
            System.out.println("1. Insert");
            System.out.println("2. Remove");
            System.out.println("3. emOrdem");
            System.out.println("4. Nível");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt(); // scanner pega o proximo inteiro de escolha

            switch (choice) { // de acordo com a opção
                case 1:
                    System.out.print("Valor para inserir: ");
                    value = scanner.nextInt();
                    tree.insert(value);
                    System.out.println(value + " inserted successfully!");
                    break;
                case 2:
                    System.out.print("Valor para remover: ");
                    value = scanner.nextInt();
                    tree.remove(value);
                    System.out.println(value + "Removed successfully!");
                    break;
                case 3:
                    System.out.print("Valores em ordem: ");
                    //System.out.println(tree.inOrder());
                    tree.inOrder();

                    break;
                case 4:
                    System.out.println("Estrutura por nível:");
                    System.out.println(tree.toString());
                    //tree.passeioNivel();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 5);


        scanner.close();
    }


}