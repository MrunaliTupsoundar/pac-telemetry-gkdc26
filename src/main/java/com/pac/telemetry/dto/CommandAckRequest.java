package com.pac.telemetry.dto;

import jakarta.validation.constraints.NotNull;

public class CommandAckRequest {

    public enum AckStatus {
        EXECUTED,
        FAILED
    }

    @NotNull
    private AckStatus status;

    private String note;

    public AckStatus getStatus() {
        return status;
    }

    public void setStatus(AckStatus status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}