package br.com.estruturas.app;

import br.com.estruturas.avl.AVLTree;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AVLTree<Integer> tree = new AVLTree<>();
        int choice, value;

        do {
            System.out.println("\n--- AVL Tree Operations ---");
            System.out.println("1. Insert");
            System.out.println("2. Remove");
            System.out.println("3. In-Order Traversal");
            System.out.println("4. Display Tree Structure");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter value to insert: ");
                    value = scanner.nextInt();
                    tree.insert(value);
                    System.out.println(value + " inserted successfully!");
                    break;
                case 2:
                    System.out.print("Enter value to remove: ");
                    value = scanner.nextInt();
                    // tree.remove(value); // Uncomment when remove is implemented
                    System.out.println("Remove operation will be implemented here");
                    break;
                case 3:
                    System.out.print("In-Order Traversal: ");
                    // tree.inOrderTraversal(); // Uncomment when traversal is implemented
                    System.out.println("Traversal will be implemented here");
                    break;
                case 4:
                    System.out.println("Tree Structure:");
                    // tree.display(); // Uncomment when display is implemented
                    System.out.println("Display will be implemented here");
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