package com.pac.telemetry.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TelemetryRequest {

    @DecimalMin("0.0")
    private BigDecimal hvBatteryVoltage;

    private BigDecimal currentDrawn;

    private Integer rpm;

    private BigDecimal motorTemp;

    private BigDecimal controllerTemp;

    private LocalDateTime logTimestamp;

    public BigDecimal getHvBatteryVoltage() {
        return hvBatteryVoltage;
    }

    public void setHvBatteryVoltage(BigDecimal hvBatteryVoltage) {
        this.hvBatteryVoltage = hvBatteryVoltage;
    }

    public BigDecimal getCurrentDrawn() {
        return currentDrawn;
    }

    public void setCurrentDrawn(BigDecimal currentDrawn) {
        this.currentDrawn = currentDrawn;
    }

    public Integer getRpm() {
        return rpm;
    }

    public void setRpm(Integer rpm) {
        this.rpm = rpm;
    }

    public BigDecimal getMotorTemp() {
        return motorTemp;
    }

    public void setMotorTemp(BigDecimal motorTemp) {
        this.motorTemp = motorTemp;
    }

    public BigDecimal getControllerTemp() {
        return controllerTemp;
    }

    public void setControllerTemp(BigDecimal controllerTemp) {
        this.controllerTemp = controllerTemp;
    }

    public LocalDateTime getLogTimestamp() {
        return logTimestamp;
    }

    public void setLogTimestamp(LocalDateTime logTimestamp) {
        this.logTimestamp = logTimestamp;
    }
}