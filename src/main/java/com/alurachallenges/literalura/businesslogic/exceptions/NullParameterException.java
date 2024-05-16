package com.alurachallenges.literalura.businesslogic.exceptions;

public class NullParameterException extends RuntimeException {
    private final String message;

    public NullParameterException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
