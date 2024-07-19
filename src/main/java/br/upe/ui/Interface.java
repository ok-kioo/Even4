package br.upe.ui;

import br.upe.controller.Controller;
import br.upe.controller.UserController;

import java.util.Scanner;



public class Interface {
    public static void main(String[] args) {
        System.out.println("Bem-Vindo ao Even4");
        int option = -1;

        try (Scanner sc = new Scanner(System.in)) {
            do {
                System.out.println("[1] - Login");
                System.out.println("[2] - Cadastrar");
                System.out.println("[0] - Sair");
                System.out.print("Escolha uma opção: ");
                if (sc.hasNextInt()) {
                    option = sc.nextInt();
                    sc.nextLine();
                } else {
                    System.out.println("Entrada inválida. Por favor, insira um número.");
                    sc.nextLine();
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
            System.out.println("Digite seu cpf");
            String cpf = sc.nextLine();
            Controller userController = new UserController();
            if(userController.loginValidate(email, cpf)) {
                System.out.println("Login Realizado com Sucesso");

            } else {
                System.out.println("Login ou senha incorreto");
            }

        } else {
            System.out.println("Erro ao ler email.");
        }
    }

    public static void signup(Scanner sc) {
        System.out.println("Cadastre seu email:");
        if (sc.hasNextLine()) {
            String email = sc.nextLine();
            System.out.println("Digite seu cpf:");
            if (sc.hasNextLine()) {
                String cpf = sc.nextLine();
                Controller userController = new UserController();
                userController.create(email, cpf);
            } else {
                System.out.println("Erro ao ler nome.");
            }
        } else {
            System.out.println("Erro ao ler email.");
        }
    }
}