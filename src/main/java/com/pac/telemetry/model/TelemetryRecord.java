package com.pac.telemetry.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ev_telemetry")
public class TelemetryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hv_battery_voltage")
    private BigDecimal hvBatteryVoltage;

    @Column(name = "current_drawn")
    private BigDecimal currentDrawn;

    @Column(name = "rpm")
    private Integer rpm;

    @Column(name = "motor_temp")
    private BigDecimal motorTemp;

    @Column(name = "controller_temp")
    private BigDecimal controllerTemp;

    @Column(name = "log_timestamp", nullable = false)
    private LocalDateTime logTimestamp;

    public Long getId() {
        return id;
    }

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