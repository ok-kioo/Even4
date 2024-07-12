package br.upe.ui;

import java.util.Scanner;

public class Interface
{
    public static void main( String[] args )
    {
        System.out.println( "Bem-Vindo ao Even4" );
        int option = -1;
        Scanner sc = new Scanner( System.in );
        String email = "";
        do {
            System.out.println( "[ 1 ] - Login" );
            System.out.println( "[ 2 ] - Cadastrar" );
            System.out.println( "[ 0 ] - Sair" );
            option = sc.nextInt();
            sc.nextLine();
            switch (option) {
                case 1:
                    System.out.println( "Digite seu email:" );
                    email = sc.nextLine();

                    //validar email
                    break;
                case 2:
                    System.out.println( "Cadastre seu email:" );
                    email = sc.nextLine();
                    System.out.println( "digite seu nome:" );
                    String nome = sc.nextLine();

                    //cadastrar email
            }

        } while(option != 0);
        sc.close();
    }
}
