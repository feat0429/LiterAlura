package com.alurachallenges.literalura.businesslogic.exceptions;

public class InvalidLanguageException extends RuntimeException{
    private final String message;

    public InvalidLanguageException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
