package com.pac.telemetry.dto;

public class CommandResponse {

    private Long commandId;
    private String commandType;
    private String payloadJson;

    public CommandResponse() {
    }

    public CommandResponse(Long commandId, String commandType, String payloadJson) {
        this.commandId = commandId;
        this.commandType = commandType;
        this.payloadJson = payloadJson;
    }

    public Long getCommandId() {
        return commandId;
    }

    public void setCommandId(Long commandId) {
        this.commandId = commandId;
    }

    public String getCommandType() {
        return commandType;
    }

    public void setCommandType(String commandType) {
        this.commandType = commandType;
    }

    public String getPayloadJson() {
        return payloadJson;
    }

    public void setPayloadJson(String payloadJson) {
        this.payloadJson = payloadJson;
    }
}