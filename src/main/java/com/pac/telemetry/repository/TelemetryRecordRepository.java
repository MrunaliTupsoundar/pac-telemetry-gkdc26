package com.pac.telemetry.repository;

import com.pac.telemetry.model.TelemetryRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelemetryRecordRepository extends JpaRepository<TelemetryRecord, Long> {
}