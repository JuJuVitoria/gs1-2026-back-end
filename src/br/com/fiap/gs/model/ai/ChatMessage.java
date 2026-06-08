package br.com.fiap.gs.model.ai;

import br.com.fiap.gs.enums.SenderType;

import java.time.LocalDateTime;
import java.util.UUID;

public class ChatMessage {
    private UUID id;
    private UUID idSession;
    private SenderType senderType;
    private String messageContent;
    private LocalDateTime timestamp;

    public ChatMessage(UUID idSession, SenderType senderType, String messageContent, LocalDateTime timestamp) {
        this.id = UUID.randomUUID();
        this.idSession = idSession;
        this.senderType = senderType;
        this.messageContent = messageContent;
        this.timestamp = timestamp;
    }

    public ChatMessage(UUID id, UUID idSession, SenderType senderType, String messageContent, LocalDateTime timestamp) {
        this.id = id;
        this.idSession = idSession;
        this.senderType = senderType;
        this.messageContent = messageContent;
        this.timestamp = timestamp;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdSession() {
        return idSession;
    }

    public void setIdSession(UUID idSession) {
        this.idSession = idSession;
    }

    public SenderType getSenderType() {
        return senderType;
    }

    public void setSenderType(SenderType senderType) {
        this.senderType = senderType;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return this.messageContent + " - " + this.timestamp;
    }
}
