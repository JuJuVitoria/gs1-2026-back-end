package br.com.fiap.gs.model;

import java.time.LocalDateTime;

public class ChatMessage {
    private long id;
    private long idSession;
    //Depois mudar para enum SenderType
    private String senderType;
    private String messageContent;
    private LocalDateTime timestamp;

    public ChatMessage(long idSession, String senderType, String messageContent, LocalDateTime timestamp) {
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

    public String getSenderType() {
        return senderType;
    }

    public void setSenderType(String senderType) {
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
