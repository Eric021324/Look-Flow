package com.lookflow.domain.exception;

public class WorkshiftOverlapedException extends RuntimeException {
    public WorkshiftOverlapedException(String message) {
        super(message);
    }
}
