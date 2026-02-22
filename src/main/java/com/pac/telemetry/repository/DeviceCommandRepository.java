package com.pac.telemetry.repository;

import com.pac.telemetry.model.CommandStatus;
import com.pac.telemetry.model.DeviceCommand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceCommandRepository extends JpaRepository<DeviceCommand, Long> {
    Optional<DeviceCommand> findFirstByDeviceIdAndStatusOrderByCreatedAtAsc(String deviceId, CommandStatus status);
}