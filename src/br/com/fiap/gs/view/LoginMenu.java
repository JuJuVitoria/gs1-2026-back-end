package br.com.fiap.gs.view;

import br.com.fiap.gs.model.user.Farmer;
import br.com.fiap.gs.service.impl.AuthServiceImpl;

import java.util.Optional;
import java.util.Scanner;

public class LoginMenu {
    private final Scanner scanner;
    private final AuthServiceImpl authService;
    private final MainMenu mainMenu;

    public LoginMenu(AuthServiceImpl authService,
                     MainMenu mainMenu) {
        this.scanner = new Scanner(System.in);
        this.authService = authService;
        this.mainMenu = mainMenu;
    }

    public void showLoginMenu() {

        int option = -1;

        do {

            System.out.println("""
                
                ╔═════════════════════════════════════════════════════════════════════════════════════╗
                ║                                LOGIN ASTROCROP                                      ║
                ╠═════════════════════════════════════════════════════════════════════════════════════╣
                ║                                                                                     ║
                ║  Informe suas credenciais para acessar a plataforma.                                ║
                ║                                                                                     ║
                ║    [1] Entrar                                                                       ║
                ║    [2] Cadastrar-se                                                                 ║
                ║    [3] Sobre o Projeto                                                              ║
                ║    [0] Encerrar Sistema                                                             ║
                ║                                                                                     ║
                ╚═════════════════════════════════════════════════════════════════════════════════════╝
                """);

            System.out.print("Escolha uma opção: ");

            try {
                option = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("\nOpção inválida.\n");
                continue;
            }

            switch (option) {

                case 1 -> login();

                case 2 -> register();

                case 3 -> displayAboutProject();

                case 0 -> System.out.println("\nEncerrando sistema...\n");

                default -> System.out.println("\nOpção inválida.\n");
            }

        } while (option != 0);
    }
    private void login() {

        System.out.println("""
        
        ┌─────────────────────────────────────────────────────────────────────────────────────┐
        │                                  ACESSO AO SISTEMA                                  │
        └─────────────────────────────────────────────────────────────────────────────────────┘
        """);

        System.out.print("Login: ");
        String email = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        try {
            Optional<Farmer> user = authService.login(email, senha);

            if (user.isPresent()) {
                System.out.printf("""
                
                Login realizado com sucesso!
                
                Bem-vindo(a), %s
                
                """, user.get().getName()
                );
                mainMenu.showMenu();
                return;
            }

            System.out.println("""
            
            Credenciais inválidas.
            Verifique seu login e senha.
            
            """);

        } catch (IllegalArgumentException e) {
            System.out.println("\n" + e.getMessage() + "\n");
        }
    }

    public void register() {
        System.out.println("""
            ┌─────────────────────────────────────────────────────────────────────────────────────┐
            │                               CADASTRA-SE AO SISTEMA                                │
            └─────────────────────────────────────────────────────────────────────────────────────┘
            """);

        System.out.println("Nome de usuário: ");
        String userName = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        try {
            Farmer user = authService.register(userName, email, senha);
            authService.login(email, senha);
            System.out.printf("""
                
                Cadastro realizado com sucesso!
                
                Bem-vindo(a), %s
                
                """, user.getName()
            );
            mainMenu.showMenu();

        } catch (IllegalArgumentException e) {
            System.out.println("\n" + e.getMessage() + "\n");
        }
    }

    private void displayAboutProject() {
        System.out.println("""
            
            ═══════════════════════════════════════════════════════════════════════════════════════
                                            SOBRE O ASTROCROP
            ═══════════════════════════════════════════════════════════════════════════════════════
            
            O AstroCrop é uma solução desenvolvida para a Global Solution 2026 da FIAP.

            O sistema utiliza conceitos de monitoramento climático, análise de riscos
            agrícolas e economia espacial para auxiliar produtores rurais na tomada
            de decisões mais seguras e eficientes.

            Funcionalidades:
             • Cadastro de produtores rurais;
             • Gerenciamento de propriedades;
             • Monitoramento de dados climáticos;
             • Alertas agroclimáticos;
             • Diário digital de campo;
             • Recomendações inteligentes de plantio e manejo.

            Objetivos:
             • Reduzir perdas agrícolas;
             • Apoiar a agricultura sustentável;
             • Democratizar o acesso a informações agroclimáticas;
             • Conectar tecnologias espaciais ao agronegócio.

            Projeto acadêmico desenvolvido para a disciplina
            Global Solution 2026/1 - FIAP.

            ═══════════════════════════════════════════════════════════════════════════════════════
            
            """);
    }
}
