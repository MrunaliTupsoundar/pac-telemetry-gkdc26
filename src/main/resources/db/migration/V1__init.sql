CREATE TABLE IF NOT EXISTS ev_telemetry (
    id BIGSERIAL PRIMARY KEY,
    hv_battery_voltage NUMERIC(6, 2),
    current_drawn NUMERIC(8, 2),
    rpm INTEGER,
    motor_temp NUMERIC(5, 2),
    controller_temp NUMERIC(5, 2),
    log_timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_ev_telemetry_time
    ON ev_telemetry (log_timestamp DESC);

CREATE TABLE IF NOT EXISTS device_commands (
    id BIGSERIAL PRIMARY KEY,
    device_id VARCHAR(64) NOT NULL,
    command_type VARCHAR(100) NOT NULL,
    payload_json TEXT NOT NULL,
    status VARCHAR(16) NOT NULL,
    execution_note TEXT,
    created_at TIMESTAMPTZ NOT NULL,
    sent_at TIMESTAMPTZ,
    executed_at TIMESTAMPTZ
);

CREATE INDEX IF NOT EXISTS idx_commands_device_status_created
    ON device_commands (device_id, status, created_at ASC);