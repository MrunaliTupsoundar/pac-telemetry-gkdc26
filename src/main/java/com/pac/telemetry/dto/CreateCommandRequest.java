package com.pac.telemetry.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateCommandRequest {

    @NotBlank
    private String deviceId;

    @NotBlank
    private String commandType;

    @NotBlank
    private String payloadJson;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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