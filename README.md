# EV Telemetry System (Spring Boot + PostgreSQL)

This backend accepts telemetry from ESP devices and stores it in PostgreSQL.

It also supports a command queue:
- Admin creates a command for a device
- Device polls for pending command
- Device acknowledges command execution

## 1) Run locally

Prerequisites:
- Java 17+
- Maven

This project is configured for Neon PostgreSQL.

Set environment variables (PowerShell):

```powershell
$env:SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/ev_telemetry"
$env:SPRING_DATASOURCE_URL="jdbc:postgresql://ep-old-water-a131nhal-pooler.ap-southeast-1.aws.neon.tech/neondb?sslmode=require&channelBinding=require"
$env:SPRING_DATASOURCE_USERNAME="neondb_owner"
$env:SPRING_DATASOURCE_PASSWORD="<your-neon-password>"
$env:DEVICE_API_TOKEN="your-device-token"
$env:ADMIN_API_KEY="your-admin-key"
$env:SPRING_FLYWAY_ENABLED="true"
```

Run:

```powershell
mvn spring-boot:run
```

Health check:

```bash
GET http://localhost:8080/actuator/health
```

## 2) API contract

### Device telemetry POST

`POST /api/device/telemetry`

Headers:
- `X-Device-Token: <DEVICE_API_TOKEN>`

Body:

```json
{
  "deviceId": "esp-001",
  "hvBatteryVoltage": 328.45,
  "currentDrawn": 42.10,
  "rpm": 3120,
  "motorTemp": 58.30,
  "controllerTemp": 49.80,
  "logTimestamp": "2026-02-23T10:15:30"
}
```

`logTimestamp` is optional; if omitted, the server stores current time.

### Admin queue command

`POST /api/admin/commands`

Headers:
- `X-Admin-Key: <ADMIN_API_KEY>`

Body:

```json
{
  "deviceId": "esp-001",
  "commandType": "SET_LIMIT",
  "payloadJson": "{\"speedLimitKph\":45}"
}
```

### Device poll next command

`GET /api/device/commands/next?deviceId=esp-001`

Headers:
- `X-Device-Token: <DEVICE_API_TOKEN>`

### Device ack command

`POST /api/device/commands/{commandId}/ack`

Headers:
- `X-Device-Token: <DEVICE_API_TOKEN>`

Body:

```json
{
  "status": "EXECUTED",
  "note": "Applied successfully"
}
```

## 3) Deploy to Render

This repo includes `render.yaml` for Blueprint deploy.

1. Push this project to GitHub.
2. In Render, choose **New +** -> **Blueprint**.
3. Select your GitHub repo.
4. Set env vars in Render service:
  - `SPRING_DATASOURCE_URL`
  - `SPRING_DATASOURCE_USERNAME`
  - `SPRING_DATASOURCE_PASSWORD`
  - `DEVICE_API_TOKEN`
  - `ADMIN_API_KEY`
  - `SPRING_FLYWAY_ENABLED=true`

## 5) Is V1__init.sql required?

- If `SPRING_FLYWAY_ENABLED=true`: yes, keep `V1__init.sql` so required tables/indexes are created safely.
- If your Neon DB already has *all* required tables (`ev_telemetry` and `device_commands`) and you set `SPRING_FLYWAY_ENABLED=false`, then migration is not required at runtime.

## 4) ESP example (HTTP)

ESP should send:
- URL: `https://<your-render-domain>/api/device/telemetry`
- Header: `X-Device-Token`
- Content-Type: `application/json`

Then poll command endpoint periodically and acknowledge execution.