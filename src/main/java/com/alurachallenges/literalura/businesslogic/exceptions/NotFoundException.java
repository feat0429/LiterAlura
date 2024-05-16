package com.alurachallenges.literalura.businesslogic.exceptions;

public class NotFoundException extends RuntimeException{
    private final String message;

    public NotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
