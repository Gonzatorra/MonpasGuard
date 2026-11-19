# Monpás Guard

Sistema IoT de seguridad costera para zonas peligrosas de Donostia, como Monpás y Paseo Nuevo.

El prototipo detecta situaciones de riesgo mediante sensores conectados a una Raspberry Pi, activa avisos locales mediante LED y buzzer, registra eventos en Supabase y los muestra en Power BI.

## Objetivo principal

Construir el flujo completo:

```text
Sensores y botón SOS
        ↓
Raspberry Pi
        ↓
LED y buzzer
        ↓ HTTPS
Render API
        ↓
Supabase
        ↓
Power BI
```

> La alerta local debe funcionar aunque no haya Internet. La Raspberry Pi nunca dependerá de Render, Supabase o Power BI para activar LED y buzzer.

## Componentes

- Raspberry Pi.
- Sensor ultrasónico.
- Sensor de temperatura y humedad DHT11.
- Botón SOS con LED.
- Buzzer.
- Render: API y servidor backend.
- Supabase: Base de datos cloud.
- Power BI: Dashboard y análisis.
- MQTT: Comunicación IoT en tiempo real.
- Web mobile-responsive: SOS desde teléfono.
- MCP: Agente IA de apoyo a operadores.

---

# Reparto del equipo

Cada persona tiene un área principal, pero las tres deben entender el flujo completo antes de la presentación final.

| Área | Responsabilidad | Resultado esperado |
|---|---|---|
| Raspberry Pi e IoT | Sensores, actuadores, alertas locales, SQLite y envío de eventos | Estación física funcional |
| Cloud y backend | Render, Supabase, API, seguridad y MQTT | Eventos almacenados correctamente en cloud |
| Web, Power BI e IA | Aplicación SOS, dashboard, panel de operador y MCP | Información útil y accesible para ciudadanía y operadores |

---

# 1. Raspberry Pi e IoT

## 1.1 Pruebas individuales completadas

- [x] Sensor ultrasónico: Lectura de distancia en centímetros.
- [x] Sensor de temperatura y humedad: Lecturas reales de temperatura y humedad.
- [x] Buzzer: Encendido y apagado.
- [x] Botón SOS: Detección de pulsación y LED de confirmación.
- [x] Botón SOS + buzzer: Alerta local conjunta.

## 1.2 Lógica local de alertas

- [ ] Crear una estructura común para sensores, actuadores y reglas.
- [ ] Leer distancia del ultrasónico periódicamente.
- [ ] Leer temperatura y humedad periódicamente.
- [ ] Definir `DISTANCE_THRESHOLD`.
- [ ] Simular el riesgo marítimo con `Calm`, `Moderate` y `High`.
- [ ] Si hay proximidad y el riesgo es `High`, activar LED y buzzer.
- [ ] Detectar subida rápida de humedad y descenso de temperatura.
- [ ] Si hay cambio ambiental y el riesgo es `High`, activar LED y buzzer.
- [ ] Crear un evento SOS local al pulsar el botón.
- [ ] Evitar alertas repetidas mediante un tiempo de espera.
- [ ] Registrar lecturas, alertas y errores en logs.

## 1.3 Resiliencia local y estado de la estación

- [ ] Crear una cola SQLite para eventos pendientes de enviar.
- [ ] Reintentar el envío cuando vuelva Internet.
- [ ] Recoger uso de CPU, RAM, disco, temperatura de CPU y Wi-Fi.
- [ ] Registrar la última conexión correcta con el cloud.
- [ ] Comprobar que LED y buzzer funcionan sin Internet.

---

# 2. Cloud, Render, Supabase y MQTT

## 2.1 Supabase

- [ ] Crear proyecto en Supabase.
- [ ] Guardar claves y secretos en variables de entorno.
- [ ] Crear tabla `stations`.
- [ ] Crear tabla `station_events`.
- [ ] Crear tabla `sos_reports`.
- [ ] Crear tabla `device_metrics`.
- [ ] Crear tabla `station_config`.
- [ ] Crear tabla `pending_actions`.
- [ ] Crear tabla `audit_log`.
- [ ] Definir claves primarias, relaciones, timestamps y restricciones.
- [ ] Aplicar seguridad mediante RLS o acceso limitado desde la API.
- [ ] No subir nunca claves o secretos a GitHub.

## 2.2 Render y FastAPI

- [ ] Crear proyecto FastAPI.
- [ ] Desplegar la API en Render.
- [ ] Crear endpoint `GET /health`.
- [ ] Crear endpoint `POST /events`.
- [ ] Crear endpoint `POST /sos`.
- [ ] Crear endpoint `POST /metrics`.
- [ ] Crear endpoint `GET /stations/{station_id}/config`.
- [ ] Validar los datos antes de guardarlos.
- [ ] Usar `event_id` único para evitar duplicados.
- [ ] Guardar los datos validados en Supabase.

## 2.3 MQTT

- [ ] Elegir y configurar un broker MQTT.
- [ ] Crear tópicos para eventos, estado, métricas y configuración.
- [ ] Conectar la Raspberry mediante MQTTS.
- [ ] Publicar eventos y estado de la estación.
- [ ] Suscribir la Raspberry a cambios de configuración.
- [ ] Documentar tópicos, credenciales y formato de mensajes.

Tópicos previstos:

```text
monpas/stations/MONPAS-01/events
monpas/stations/MONPAS-01/status
monpas/stations/MONPAS-01/metrics
monpas/stations/MONPAS-01/config
```

---

# 3. Web móvil, Power BI y MCP

## 3.1 Aplicación web mobile-responsive

- [ ] Crear formulario SOS adaptado a móvil.
- [ ] Incluir estación o ubicación.
- [ ] Incluir tipo de incidente.
- [ ] Incluir descripción opcional.
- [ ] Enviar SOS a Render mediante HTTPS.
- [ ] Mostrar el mensaje “Report registered”.
- [ ] No indicar que la ayuda está en camino.
- [ ] Crear una vista básica para operadores con SOS abiertos.

## 3.2 Power BI

- [ ] Conectar Power BI con datos reales de Supabase.
- [ ] Crear tabla de hechos y dimensiones de estación, fecha, fuente y riesgo.
- [ ] Crear KPIs: Total Events, Warnings Activated, SOS Events y Warning Rate.
- [ ] Mostrar eventos por fuente, hora, estación y riesgo marítimo.
- [ ] Mostrar alertas activadas.
- [ ] Mostrar SOS abiertos.
- [ ] Mostrar eventos recientes.
- [ ] Mostrar métricas técnicas de la Raspberry.
- [ ] Etiquetar claramente cualquier dato simulado.
- [ ] Probar que otra persona puede actualizar Power BI mientras alguien usa la Raspberry.

## 3.3 MCP y agente IA

> Esta parte se implementará solo cuando el flujo Raspberry → Render → Supabase → Power BI esté funcionando.

- [ ] Crear herramienta `get_station_status`.
- [ ] Crear herramienta `get_recent_events`.
- [ ] Crear herramienta `get_open_sos`.
- [ ] Crear herramienta `get_sea_conditions`.
- [ ] Crear herramienta `acknowledge_sos`.
- [ ] Crear herramienta `request_notification`.
- [ ] Crear herramienta `request_risk_override`.
- [ ] Guardar las acciones sensibles en `pending_actions`.
- [ ] Exigir aprobación humana antes de ejecutar acciones.
- [ ] Registrar llamadas MCP y aprobaciones en `audit_log`.
- [ ] No permitir que la IA cierre SOS, contacte con 112, reduzca el riesgo o desactive alertas.

---

# Hitos compartidos

## Formato común de evento

```json
{
  "event_id": "uuid",
  "station_id": "MONPAS-01",
  "source": "ultrasonic",
  "event_type": "proximity_detected",
  "value": 35.4,
  "sea_risk": "High",
  "warning_activated": true,
  "timestamp": "2026-11-19T15:00:00Z"
}
```

## Pruebas finales

- [ ] Proximidad + riesgo `High` activa LED y buzzer.
- [ ] Proximidad + riesgo `Calm` no activa alerta.
- [ ] Cambio ambiental + riesgo `High` activa LED y buzzer.
- [ ] Pulsación física SOS crea un evento.
- [ ] Evento de la Raspberry llega a Render.
- [ ] Render guarda el evento en Supabase.
- [ ] Power BI muestra el evento real.
- [ ] La alerta local sigue funcionando sin Internet.
- [ ] Web móvil crea un SOS en Supabase.
- [ ] MCP consulta datos reales y crea solicitudes pendientes.
- [ ] Preparar capturas o vídeos de cada prueba.
- [ ] Preparar documentación, esquema de base de datos y presentación.

---

# Mínimo producto viable

El MVP estará completado cuando un evento real generado por la Raspberry llegue a Supabase y aparezca en Power BI, mientras LED y buzzer funcionan localmente aunque no haya conexión a Internet.

## Estructura prevista del repositorio

```text
MonpasGuard/
├── test_individuales/
├── raspberry/
├── backend/
├── web/
├── supabase/
├── powerbi/
└── docs/
```
