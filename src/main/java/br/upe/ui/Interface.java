package br.upe.ui;

import java.util.Scanner;

import br.upe.controller.Controller;
import br.upe.controller.UserController;


public class Interface
{
    public static void main( String[] args )
    {
        System.out.println( "Bem-Vindo ao Even4" );
        int option;
        try (Scanner sc = new Scanner( System.in )) {
            do {
                System.out.println( "[ 1 ] - Login" );
                System.out.println( "[ 2 ] - Cadastrar" );
                System.out.println( "[ 0 ] - Sair" );
                option = sc.nextInt();
                sc.nextLine();
                switch (option) {
                    case 1 -> login();
                    case 2 -> signin();
                }
                
            } while(option != 0);
        }
    }

    public static void login() {
        try (Scanner sc = new Scanner( System.in )) {
            System.out.println( "Digite seu email:" );
            String email = sc.nextLine();
            //validar email
        }
    }

    public static void signin() {
        try (Scanner sc = new Scanner( System.in )) {
            System.out.println( "Cadastre seu email:" );
            String email = sc.nextLine();
            System.out.println( "digite seu nome:" );
            String name = sc.nextLine();
            
            Controller userController = new UserController();
            userController.create(email, name);
        }
    }
}
