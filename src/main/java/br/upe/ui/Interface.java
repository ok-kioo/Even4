package br.upe.ui;

import br.upe.controller.Controller;
import br.upe.controller.EventController;
import br.upe.controller.SubEventController;
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
                }

                switch (option) {
                    case 1:
                        Object[] results = login(sc);
                        boolean isLog = (boolean) results[0];
                        Controller userLogin = (Controller) results[1];

                        if(isLog) {
                            do {
                                System.out.println("[1] - Criar Evento");
                                System.out.println("[2] - Entrar em um Evento");
                                System.out.println("[3] - Criar SubEvento");
                                System.out.println("[4] - Criar Sessão");
                                System.out.println("[5] - Perfil");
                                System.out.println("[0] - Voltar");
                                System.out.print("Escolha uma opção: ");
                                if (sc.hasNextInt()) {
                                    option = sc.nextInt();
                                    sc.nextLine();
                                } else {
                                    System.out.println("Entrada inválida. Por favor, insira um número.");
                                    sc.nextLine();
                                }


                                switch (option) {

                                    case 1:
                                        Controller ec = new EventController();
                                        System.out.println("Digite o nome do Evento: ");
                                        String nameEvent = sc.nextLine();
                                        System.out.println("Data do Evento: ");
                                        String dateEvent = sc.nextLine();
                                        System.out.println("Descrição do Evento: ");
                                        String descriptionEvent = sc.nextLine();
                                        System.out.println("Local do Evento: ");
                                        String locationEvent = sc.nextLine();
                                        ec.create(nameEvent, dateEvent, descriptionEvent, locationEvent, userLogin.getData("id"));
                                        break;

                                    case 2:
                                        break;
                                    case 3:
                                        Controller sec = (Controller) new SubEventController();
                                        System.out.println("Digite o nome do SubEvento: ");
                                        String nameSubEvent = sc.nextLine();
                                        System.out.println("Descrição do Evento: ");
                                        String descriptionSubEvent = sc.nextLine();
                                        System.out.println("Data do Evento: ");
                                        String dateSubEvent = sc.nextLine();
                                        System.out.println("Local do Evento: ");
                                        String locationSubEvent = sc.nextLine();
                                        sec.create(nameSubEvent, dateSubEvent, descriptionSubEvent, locationSubEvent, userLogin.getData("id"));
                                        break;
                                    case 5:
                                        boolean isRemoved = setup(sc, userLogin);
                                        if (isRemoved) {
                                            option = 0;
                                        }
                                        break;
                                    case 0 :
                                        System.out.println("Voltando...");
                                        break;
                                    default:
                                        System.out.println("Opção inválida. Tente novamente.");
                                }

                            }while (option != 0);
                            option = -1;
                        }
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

    public static Object[] login(Scanner sc) {
        Controller userController = new UserController();
        System.out.println("Digite seu email:");
        boolean isLog = false;
        if (sc.hasNextLine()) {
            String email = sc.nextLine();
            System.out.println("Digite seu cpf");
            String cpf = sc.nextLine();
            if(userController.loginValidate(email, cpf)) {
                System.out.println("Login Realizado com Sucesso");
                isLog = true;
            } else {
                System.out.println("Login ou senha incorreto");
            }

        } else {
            System.out.println("Erro ao ler email.");
        }
        return new Object[] {isLog, userController};
    }

    public static void signup(Scanner sc) {
        Controller userController = new UserController();
        System.out.println("Cadastre seu email:");
        if (sc.hasNextLine()) {
            String email = sc.nextLine();
            System.out.println("Digite seu cpf:");
            if (sc.hasNextLine()) {
                String cpf = sc.nextLine();
                userController.create(email, cpf);
            } else {
                System.out.println("Erro ao ler cpf.");
            }
        } else {
            System.out.println("Erro ao ler email.");
        }
    }

    public static boolean setup(Scanner sc, Controller userLogin) {
        int option = -1;
        boolean isRemoved = false;
        do {
            System.out.println("[1] - Atualizar conta");
            System.out.println("[2] - Deletar conta");
            System.out.println("[0] - Voltar");
            System.out.print("Escolha uma opção: ");
            if (sc.hasNextInt()) {
                option = sc.nextInt();
                sc.nextLine();
            } else {
                System.out.println("Entrada inválida. Por favor, insira um número.");
                sc.nextLine();
            }

            switch (option) {
                case 1:
                    System.out.println("O que você deseja atualizar?");
                    System.out.println("[1] - email");
                    System.out.println("[2] - cpf");
                    System.out.println("[0] - voltar");
                    if (sc.hasNextInt()) {
                        option = sc.nextInt();
                        sc.nextLine();
                    } else {
                        System.out.println("Entrada inválida. Por favor, insira um número.");
                        sc.nextLine();
                    }

                    switch (option) {
                        case 1:
                            System.out.println("Digite o novo email");
                            if (sc.hasNextLine()) {
                                String email = sc.nextLine();
                                userLogin.update(email, userLogin.getData("cpf"));
                            } else {
                                System.out.println("Erro ao ler email.");
                            }
                            break;
                        case 2:
                            System.out.println("Digite o novo cpf");
                            if (sc.hasNextLine()) {
                                String cpf = sc.nextLine();
                                userLogin.update(userLogin.getData("email"), cpf);
                            } else {
                                System.out.println("Erro ao ler cpf.");
                            }
                            break;
                        case 0:
                            System.out.println("Voltando...");
                            break;

                    }
                    option = -1;
                    break;
                case 2:
                    System.out.println("Deletar Conta?");
                    System.out.println("[1] - Sim");
                    System.out.println("[2] - Não");
                    if (sc.hasNextInt()) {
                        option = sc.nextInt();
                        sc.nextLine();
                    } else {
                        System.out.println("Entrada inválida. Por favor, insira um número.");
                        sc.nextLine();
                    }
                    switch (option) {
                        case 1:
                            userLogin.deleteById(userLogin.getData("id"));
                            option = 0;
                            isRemoved = true;
                            break;
                        case 2:
                            System.out.println("Voltando..");
                            break;
                    }
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (option != 0);
        return isRemoved;
    }

}
