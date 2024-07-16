package br.upe.ui;

import br.upe.controller.Controller;
import br.upe.controller.UserController;

import java.util.Scanner;



public class Interface {
    public static void main(String[] args) {
        System.out.println("Bem-Vindo ao Even4");
        int option = -1; // Inicialize com um valor inválido para entrar no loop

        try (Scanner sc = new Scanner(System.in)) {
            do {
                System.out.println("[1] - Login");
                System.out.println("[2] - Cadastrar");
                System.out.println("[0] - Sair");
                System.out.print("Escolha uma opção: ");
                if (sc.hasNextInt()) {
                    option = sc.nextInt();
                    sc.nextLine(); // Consumir a nova linha pendente
                } else {
                    System.out.println("Entrada inválida. Por favor, insira um número.");
                    sc.nextLine(); // Limpar a entrada inválida
                    continue;
                }

                switch (option) {
                    case 1:
                        login(sc);
                        break;
                    case 2:
                        signup(sc);
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } while (option != 0);
        }
    }

    public static void login(Scanner sc) {
        System.out.println("Digite seu email:");
        if (sc.hasNextLine()) {
            String email = sc.nextLine();
            // validar email
            System.out.println("Login com email: " + email);
        } else {
            System.out.println("Erro ao ler email.");
        }
    }

    public static void signup(Scanner sc) {
        System.out.println("Cadastre seu email:");
        if (sc.hasNextLine()) {
            String email = sc.nextLine();
            System.out.println("Digite seu nome:");
            if (sc.hasNextLine()) {
                String name = sc.nextLine();
                Controller userController = new UserController();
                userController.create(email, name);
            } else {
                System.out.println("Erro ao ler nome.");
            }
        } else {
            System.out.println("Erro ao ler email.");
        }
    }
}