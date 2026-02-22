package com.pac.telemetry.controller;

import com.pac.telemetry.dto.CreateCommandRequest;
import com.pac.telemetry.model.CommandStatus;
import com.pac.telemetry.model.DeviceCommand;
import com.pac.telemetry.repository.DeviceCommandRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final DeviceCommandRepository deviceCommandRepository;

    public AdminController(DeviceCommandRepository deviceCommandRepository) {
        this.deviceCommandRepository = deviceCommandRepository;
    }

    @PostMapping("/commands")
    public ResponseEntity<Map<String, Object>> createCommand(@Valid @RequestBody CreateCommandRequest request) {
        DeviceCommand command = new DeviceCommand();
        command.setDeviceId(request.getDeviceId());
        command.setCommandType(request.getCommandType());
        command.setPayloadJson(request.getPayloadJson());
        command.setStatus(CommandStatus.PENDING);
        command.setCreatedAt(OffsetDateTime.now());

        DeviceCommand saved = deviceCommandRepository.save(command);
        return ResponseEntity.ok(Map.of("status", "queued", "commandId", saved.getId()));
    }
}