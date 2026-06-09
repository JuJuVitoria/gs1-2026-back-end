package br.com.fiap.gs.service.impl;

import br.com.fiap.gs.enums.SenderType;
import br.com.fiap.gs.model.ai.ChatMessage;
import br.com.fiap.gs.model.ai.ChatSession;
import br.com.fiap.gs.repository.impl.ai.ChatMessageRepository;
import br.com.fiap.gs.repository.impl.ai.ChatSessionRepository;
import br.com.fiap.gs.service.interfaces.ChatService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ChatServiceImpl implements ChatService {

    private final ChatSessionRepository sessionRepository;
    private final ChatMessageRepository messageRepository;

    private static final DateTimeFormatter TIME_FMT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public ChatServiceImpl(ChatSessionRepository sessionRepository,
                           ChatMessageRepository messageRepository) {
        this.sessionRepository = sessionRepository;
        this.messageRepository = messageRepository;
    }

    // -------------------------------------------------------------------------
    // Sessões
    // -------------------------------------------------------------------------

    @Override
    public ChatSession createSession(UUID idFarmer, String topic) {
        if (topic == null || topic.isBlank())
            throw new IllegalArgumentException("O tópico da sessão não pode ser vazio.");

        ChatSession session = new ChatSession(idFarmer, topic, LocalDate.now());
        sessionRepository.save(session);
        System.out.printf("[Chat] Nova sessão criada: \"%s\" (id: %s)%n", topic, session.getId());
        return session;
    }

    @Override
    public List<ChatSession> listSessionsByFarmer(UUID idFarmer) {
        return sessionRepository.findAllByFarmerId(idFarmer)
                .stream()
                .sorted(Comparator.comparing(ChatSession::getStartDate).reversed())
                .toList();
    }

    @Override
    public Optional<ChatSession> findSessionById(UUID sessionId) {
        return sessionRepository.findById(sessionId);
    }

    @Override
    public List<ChatSession> searchSessionsByTopic(String topic) {
        if (topic == null || topic.isBlank())
            throw new IllegalArgumentException("Informe um tópico para pesquisar.");
        return sessionRepository.findByTopic(topic);
    }

    @Override
    public void deleteSession(UUID sessionId) {
        messageRepository.findAllBySessionId(sessionId)
                .stream()
                .map(ChatMessage::getId)
                .forEach(messageRepository::deleteById);

        sessionRepository.deleteById(sessionId);
        System.out.printf("[Chat] Sessão %s e suas mensagens foram removidas.%n", sessionId);
    }

    // -------------------------------------------------------------------------
    // Mensagens
    // -------------------------------------------------------------------------

    @Override
    public ChatMessage sendMessage(UUID sessionId, SenderType senderType, String content) {
        if (content == null || content.isBlank())
            throw new IllegalArgumentException("O conteúdo da mensagem não pode ser vazio.");

        sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Sessão não encontrada: " + sessionId));

        ChatMessage message = new ChatMessage(
                sessionId, senderType, content, LocalDateTime.now()
        );
        messageRepository.save(message);
        return message;
    }

    @Override
    public List<ChatMessage> listMessages(UUID sessionId) {
        return messageRepository.findAllBySessionId(sessionId)
                .stream()
                .sorted(Comparator.comparing(ChatMessage::getTimestamp))
                .toList();
    }

    @Override
    public List<ChatMessage> listMessagesBySender(UUID sessionId, SenderType senderType) {
        return messageRepository.findAllBySessionIdAndSenderType(sessionId, senderType)
                .stream()
                .sorted(Comparator.comparing(ChatMessage::getTimestamp))
                .toList();
    }

    // -------------------------------------------------------------------------
    // Conversa completa
    // -------------------------------------------------------------------------

    @Override
    public void printConversation(UUID sessionId) {
        ChatSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Sessão não encontrada: " + sessionId));

        System.out.println("\n");
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.printf ("║  Sessão: %-54s║%n", session.getTopic());
        System.out.printf ("║  Iniciada em: %-49s║%n", session.getStartDate());
        System.out.println("╠════════════════════════════════════════════════════════════════╣");

        List<ChatMessage> messages = listMessages(sessionId);

        if (messages.isEmpty()) {
            System.out.println("║  (nenhuma mensagem ainda)                                         ║");
        } else {
            for (ChatMessage msg : messages) {
                String label   = msg.getSenderType() == SenderType.FARMER ? "Produtor" : "IA";
                String time    = msg.getTimestamp().format(TIME_FMT);
                String content = msg.getMessageContent();

                System.out.printf("║  [%s] %s%n", time, label);

                // quebra o texto em linhas de até 52 caracteres
                for (int i = 0; i < content.length(); i += 52) {
                    int end  = Math.min(i + 55, content.length());
                    System.out.printf("║    %-60s║%n", content.substring(i, end));
                }
                System.out.println("║                                                                ║");
            }
        }
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }

    @Override
    public String getConversationSummary(UUID sessionId) {
        ChatSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Sessão não encontrada: " + sessionId));

        long totalFarmer = messageRepository
                .findAllBySessionIdAndSenderType(sessionId, SenderType.FARMER).size();
        long totalBot    = messageRepository
                .findAllBySessionIdAndSenderType(sessionId, SenderType.BOT).size();

        Optional<ChatMessage> lastMsg = messageRepository.findAllBySessionId(sessionId)
                .stream()
                .max(Comparator.comparing(ChatMessage::getTimestamp));

        String lastTime = lastMsg
                .map(m -> m.getTimestamp().format(TIME_FMT))
                .orElse("—");

        return String.format(
                "[Chat] Sessão \"%s\" | Início: %s | %d msg(s) do produtor, %d resposta(s) da IA | Última: %s",
                session.getTopic(),
                session.getStartDate(),
                totalFarmer,
                totalBot,
                lastTime
        );
    }
}