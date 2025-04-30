package com.example._Pearls.response;

public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }
    public MessageResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
