package com.pac.telemetry.controller;

import com.pac.telemetry.dto.CommandAckRequest;
import com.pac.telemetry.dto.CommandResponse;
import com.pac.telemetry.dto.TelemetryRequest;
import com.pac.telemetry.model.CommandStatus;
import com.pac.telemetry.model.DeviceCommand;
import com.pac.telemetry.model.TelemetryRecord;
import com.pac.telemetry.repository.DeviceCommandRepository;
import com.pac.telemetry.repository.TelemetryRecordRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/device")
public class DeviceController {

    private final TelemetryRecordRepository telemetryRecordRepository;
    private final DeviceCommandRepository deviceCommandRepository;

    public DeviceController(TelemetryRecordRepository telemetryRecordRepository,
                            DeviceCommandRepository deviceCommandRepository) {
        this.telemetryRecordRepository = telemetryRecordRepository;
        this.deviceCommandRepository = deviceCommandRepository;
    }

    @PostMapping("/telemetry")
    public ResponseEntity<Map<String, Object>> ingestTelemetry(@Valid @RequestBody TelemetryRequest request) {
        TelemetryRecord record = new TelemetryRecord();
        record.setHvBatteryVoltage(request.getHvBatteryVoltage());
        record.setCurrentDrawn(request.getCurrentDrawn());
        record.setRpm(request.getRpm());
        record.setMotorTemp(request.getMotorTemp());
        record.setControllerTemp(request.getControllerTemp());
        record.setLogTimestamp(request.getLogTimestamp() != null ? request.getLogTimestamp() : LocalDateTime.now());

        TelemetryRecord saved = telemetryRecordRepository.save(record);
        return ResponseEntity.ok(Map.of("status", "ok", "telemetryId", saved.getId()));
    }

    @GetMapping("/commands/next")
    public ResponseEntity<?> pollNextCommand(@RequestParam String deviceId) {
        Optional<DeviceCommand> commandOpt =
                deviceCommandRepository.findFirstByDeviceIdAndStatusOrderByCreatedAtAsc(deviceId, CommandStatus.PENDING);

        if (commandOpt.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        DeviceCommand command = commandOpt.get();
        command.setStatus(CommandStatus.SENT);
        command.setSentAt(OffsetDateTime.now());
        deviceCommandRepository.save(command);

        return ResponseEntity.ok(new CommandResponse(
                command.getId(),
                command.getCommandType(),
                command.getPayloadJson()
        ));
    }

    @PostMapping("/commands/{commandId}/ack")
    public ResponseEntity<Map<String, Object>> acknowledgeCommand(@PathVariable Long commandId,
                                                                  @Valid @RequestBody CommandAckRequest request) {
        DeviceCommand command = deviceCommandRepository.findById(commandId)
                .orElseThrow(() -> new IllegalArgumentException("Command not found"));

        if (request.getStatus() == CommandAckRequest.AckStatus.EXECUTED) {
            command.setStatus(CommandStatus.EXECUTED);
        } else {
            command.setStatus(CommandStatus.FAILED);
        }

        command.setExecutionNote(request.getNote());
        command.setExecutedAt(OffsetDateTime.now());
        deviceCommandRepository.save(command);

        return ResponseEntity.ok(Map.of("status", "acknowledged", "commandId", command.getId()));
    }
}