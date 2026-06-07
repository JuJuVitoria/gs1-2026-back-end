package br.com.fiap.gs.model.ai;

import br.com.fiap.gs.enums.SenderType;

import java.time.LocalDateTime;

public class ChatMessage {
    private long id;
    private long idSession;
    private SenderType senderType;
    private String messageContent;
    private LocalDateTime timestamp;

    public ChatMessage(long idSession, SenderType senderType, String messageContent, LocalDateTime timestamp) {
        this.idSession = idSession;
        this.senderType = senderType;
        this.messageContent = messageContent;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdSession() {
        return idSession;
    }

    public void setIdSession(long idSession) {
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
