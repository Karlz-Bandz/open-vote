package com.izzisoft.open_vote.exception;

public class ElectionNotFoundException extends RuntimeException {
    public ElectionNotFoundException(String message) {
        super(message);
    }
}
