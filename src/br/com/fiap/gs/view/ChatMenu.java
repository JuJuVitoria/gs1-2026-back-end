package br.com.fiap.gs.view;

import br.com.fiap.gs.enums.SenderType;
import br.com.fiap.gs.model.ai.ChatMessage;
import br.com.fiap.gs.model.ai.ChatSession;
import br.com.fiap.gs.service.impl.AuthServiceImpl;
import br.com.fiap.gs.service.interfaces.ChatService;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ChatMenu {

    private final ChatService chatService;
    private final AuthServiceImpl authService;
    private final Scanner scanner;

    // Sessão aberta no momento (null = nenhuma)
    private ChatSession activeSession;

    public ChatMenu(ChatService chatService, AuthServiceImpl authService, Scanner scanner) {
        this.chatService = chatService;
        this.authService = authService;
        this.scanner     = scanner;
    }

    public void show() {
        int option = -1;
        do {
            printHeader();
            System.out.print("Opção: ");

            try {
                option = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida.");
                continue;
            }

            switch (option) {
                case 1 -> newSession();
                case 2 -> openSession();
                case 3 -> listSessions();
                case 4 -> deleteSession();
                case 0 -> System.out.println("\nVoltando ao menu principal...\n");
                default -> System.out.println("Opção inválida.");
            }
        } while (option != 0);
    }

    private void newSession() {
        System.out.println("""
            
            ┌─────────────────────────────────────────────────────────────────────────────────────┐
            │                               NOVA SESSÃO DE CHAT                                   │
            └─────────────────────────────────────────────────────────────────────────────────────┘
            """);

        UUID idFarmer = resolveLoggedFarmerID();
        if (idFarmer == null) return;

        System.out.print("Assunto da conversa: ");
        String topic = scanner.nextLine().trim();

        try {
            activeSession = chatService.createSession(idFarmer, topic);
            System.out.printf("%nSessão \"%s\" iniciada!%n", topic);
            runConversation();
        } catch (IllegalArgumentException e) {
            System.out.println(" X " + e.getMessage());
        }
    }

    private void openSession() {
        UUID idFarmer = resolveLoggedFarmerID();
        if (idFarmer == null) return;

        List<ChatSession> sessions = chatService.listSessionsByFarmer(idFarmer);
        if (sessions.isEmpty()) {
            System.out.println("\nVocê não possui nenhuma sessão de chat. Crie uma nova.\n");
            return;
        }

        printSessionTable(sessions, "SELECIONE UMA SESSÃO");

        System.out.print("Escolha (0 para cancelar): ");
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx == -1) return;
            if (idx < 0 || idx >= sessions.size()) {
                System.out.println("Número fora do intervalo.");
                return;
            }
            activeSession = sessions.get(idx);
            System.out.printf("%nRetomando sessão: \"%s\"%n", activeSession.getTopic());
            chatService.printConversation(activeSession.getId());
            runConversation();
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
        }
    }

    private void listSessions() {
        UUID idFarmer = resolveLoggedFarmerID();
        if (idFarmer == null) return;

        List<ChatSession> sessions = chatService.listSessionsByFarmer(idFarmer);
        if (sessions.isEmpty()) {
            System.out.println("\nNenhuma sessão encontrada.\n");
            return;
        }

        printSessionTable(sessions, "HISTÓRICO DE SESSÕES");
        sessions.forEach(s -> System.out.println("  " + chatService.getConversationSummary(s.getId())));
    }

    private void deleteSession() {
        UUID idFarmer = resolveLoggedFarmerID();
        if (idFarmer == null) return;

        List<ChatSession> sessions = chatService.listSessionsByFarmer(idFarmer);
        if (sessions.isEmpty()) {
            System.out.println("\nNenhuma sessão para remover.\n");
            return;
        }

        printSessionTable(sessions, "REMOVER SESSÃO");

        System.out.print("Escolha (0 para cancelar): ");
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx == -1) return;
            if (idx < 0 || idx >= sessions.size()) {
                System.out.println("Número fora do intervalo.");
                return;
            }
            ChatSession toDelete = sessions.get(idx);

            System.out.printf("Confirma remoção da sessão \"%s\"? (s/n): ", toDelete.getTopic());
            if (scanner.nextLine().trim().equalsIgnoreCase("s")) {
                chatService.deleteSession(toDelete.getId());
                if (activeSession != null && activeSession.getId().equals(toDelete.getId())) {
                    activeSession = null;
                }
                System.out.println("Sessão removida.");
            } else {
                System.out.println("Operação cancelada.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
        }
    }

    private void runConversation() {
        System.out.println("""
            
            ┌─────────────────────────────────────────────────────────────────────────────────────┐
            │  Digite sua mensagem e pressione Enter. Digite "sair" para encerrar a conversa.     │
            └─────────────────────────────────────────────────────────────────────────────────────┘
            """);

        while (true) {
            System.out.print("Você: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("sair")) {
                System.out.println("\nConversa encerrada. Sessão salva.\n");
                break;
            }

            if (input.isBlank()) continue;

            // Salva mensagem do produtor
            chatService.sendMessage(activeSession.getId(), SenderType.FARMER, input);

            // Gera e salva resposta da IA
            String aiReply = generateAIReply(input);
            ChatMessage botMsg = chatService.sendMessage(activeSession.getId(), SenderType.BOT, aiReply);

            System.out.println("\nIA Agro: " + botMsg.getMessageContent() + "\n");
        }
    }

    private String generateAIReply(String userMessage) {
        String msg = userMessage.toLowerCase();

        if (msg.contains("irrigar") || msg.contains("irrigação") || msg.contains("água"))
            return "Para uma irrigação eficiente, verifique a umidade do solo a 10 cm de profundidade. " +
                    "Em dias quentes (>30°C), prefira irrigar nas primeiras horas da manhã ou ao entardecer.";

        if (msg.contains("praga") || msg.contains("inseto") || msg.contains("lagarta"))
            return "O monitoramento de pragas deve ser feito 3x por semana. " +
                    "Identifique a praga antes de aplicar qualquer produto — o controle biológico " +
                    "é sempre a primeira opção recomendada.";

        if (msg.contains("adubo") || msg.contains("fertilizante") || msg.contains("npk"))
            return "A adubação deve ser baseada na análise de solo. De forma geral: " +
                    "nitrogênio (N) estimula folhas, fósforo (P) raízes e potássio (K) frutos. " +
                    "Evite excesso de N após o florescimento.";

        if (msg.contains("colheit") || msg.contains("safra"))
            return "O momento ideal de colheita varia por cultura. Verifique o teor de umidade " +
                    "dos grãos (ideal: 14%) e realize a operação nas primeiras horas da manhã " +
                    "para preservar a qualidade.";

        if (msg.contains("clima") || msg.contains("chuva") || msg.contains("seca") || msg.contains("geada"))
            return "Acompanhe os alertas climáticos no menu 'Agro / Clima'. Em caso de geada prevista, " +
                    "proteja as mudas com cobertura e evite irrigação noturna.";

        if (msg.contains("plant") || msg.contains("semear") || msg.contains("semeadura"))
            return "O plantio deve respeitar o zoneamento agroclimático da sua região. " +
                    "Confira a janela de plantio recomendada em 'Agro / Clima > Central de Inteligência'.";

        if (msg.contains("solo") || msg.contains("ph") || msg.contains("calcário"))
            return "O pH ideal para a maioria das culturas é entre 6.0 e 6.5. " +
                    "A calagem deve ser feita 60 a 90 dias antes do plantio para ser eficaz.";

        // Resposta genérica
        return "Entendido! Para uma recomendação mais precisa, consulte o menu " +
                "'Agro / Clima > Central de Inteligência' informando a fase de plantio " +
                "e o clima atual da sua região.";
    }

    private void printHeader() {
        String sessionLabel = (activeSession != null)
                ? "Sessão ativa: " + activeSession.getTopic()
                : "Nenhuma sessão ativa";

        System.out.println("\n╔═════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.printf( "║      %-79s║%n", sessionLabel);
        System.out.println("╠═════════════════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║                                                                                     ║");
        System.out.println("║       1. Nova conversa                                                              ║");
        System.out.println("║       2. Abrir conversa existente                                                   ║");
        System.out.println("║       3. Listar conversas                                                           ║");
        System.out.println("║       4. Remover conversa                                                           ║");
        System.out.println("║       0. Voltar                                                                     ║");
        System.out.println("║                                                                                     ║");
        System.out.println("╚═════════════════════════════════════════════════════════════════════════════════════╝");
    }

    private void printSessionTable(List<ChatSession> sessions, String title) {
        System.out.println("\n╔══════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.printf( "║      %-80s║%n", title);
        System.out.println("╠═══════╦══════════════════════════════════════════════════════╦═══════════════════════╣");
        System.out.println("║   #   ║  Assunto                                             ║  Iniciada em          ║");
        System.out.println("╠═══════╬══════════════════════════════════════════════════════╬═══════════════════════╣");
        for (int i = 0; i < sessions.size(); i++) {
            ChatSession s = sessions.get(i);
            System.out.printf("║  [%2d] ║  %-52s║  %-21s║%n",
                    i + 1, truncate(s.getTopic(), 48), s.getStartDate());
        }
        System.out.println("╚═══════╩══════════════════════════════════════════════════════╩═══════════════════════╝");
    }

    private String truncate(String text, int max) {
        return text.length() > max ? text.substring(0, max - 3) + "..." : text;
    }

    private UUID resolveLoggedFarmerID() {
        var farmer = authService.getLoggedUser();
        if (farmer != null) return farmer.getId();
        System.out.println("Nenhum usuário logado.");
        return null;
    }
}