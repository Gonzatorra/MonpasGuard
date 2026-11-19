# Monpas Guard

**Checklist de desarrollo: Raspberry Pi -> Supabase -> Power BI**

> **Objetivo:** completar primero el pipeline obligatorio con datos reales. MQTT, web SOS y MCP son ampliaciones que se incorporan solo cuando el flujo base sea fiable.

---

## 0. Base del proyecto y repartovc

*Cerrar el alcance antes de programar*

- [ ] Crear el repositorio GitHub, `.gitignore`, README inicial y tablero de tareas. — *Fecha / iniciales:* ___
- [ ] Confirmar los componentes disponibles y anotar modelo, alimentación y pines GPIO previstos. — *Fecha / iniciales:* ___
- [ ] Definir los identificadores: `STATION-MONPAS-01`, fuentes de evento y estados de un SOS. — *Fecha / iniciales:* ___
- [ ] Definir umbrales iniciales: distancia de proximidad, ventana de lecturas y variación ambiental. — *Fecha / iniciales:* ___
- [ ] Acordar el reparto principal: Raspberry/hardware, Supabase/Power BI y extensiones/documentación. — *Fecha / iniciales:* ___
- [ ] Establecer una reunión corta de integración semanal: todos deben entender el flujo completo. — *Fecha / iniciales:* ___

## 1. Hardware y pruebas individuales

*No integrar hasta que cada pieza funcione por separado*

- [x] Probar el sensor ultrasónico y registrar distancia en centímetros de forma estable. — *Fecha / iniciales:* ___
- [x] Probar el sensor de temperatura y humedad con lecturas periódicas reales. — *Fecha / iniciales:* ___
- [x] Probar LED y buzzer: encendido, apagado y patrón de alerta visible/audible. — *Fecha / iniciales:* ___
- [x] Probar el botón SOS, eliminar rebotes y encender el LED de confirmación. — *Fecha / iniciales:* ___
- [ ] Registrar los pines definitivos y tomar fotografías claras del cableado. — *Fecha / iniciales:* ___
- [ ] Crear un script de diagnóstico que compruebe todos los periféricos al arrancar. — *Fecha / iniciales:* ___

## 2. Lógica local en la Raspberry Pi

*La alerta física debe funcionar aunque no haya Internet*

- [ ] Programar la lectura periódica y validación de distancia, temperatura y humedad. — *Fecha / iniciales:* ___
- [ ] Implementar proximidad + riesgo High -> activar LED y buzzer. — *Fecha / iniciales:* ___
- [ ] Implementar cambio ambiental relevante + riesgo High -> activar LED y buzzer. — *Fecha / iniciales:* ___
- [ ] Implementar SOS físico -> confirmar pulsación con LED y crear evento SOS abierto. — *Fecha / iniciales:* ___
- [ ] Evitar alertas duplicadas mediante enfriamiento temporal o deduplicación. — *Fecha / iniciales:* ___
- [ ] Guardar eventos, configuración y reintentos pendientes de forma local en SQLite. — *Fecha / iniciales:* ___
- [ ] Guardar logs de lecturas, alertas, errores y reconexiones. — *Fecha / iniciales:* ___

## 3. Recursos del sistema operativo

*Usar datos de la Raspberry además de los sensores*

- [ ] Capturar temperatura de CPU, uso de CPU, RAM disponible, espacio en disco y conectividad Wi-Fi. — *Fecha / iniciales:* ___
- [ ] Enviar esas métricas periódicamente como telemetría de la estación. — *Fecha / iniciales:* ___
- [ ] Registrar última conexión y mostrar si la estación está online/offline. — *Fecha / iniciales:* ___
- [ ] Provocar una pérdida de red controlada y comprobar que la alerta local continúa funcionando. — *Fecha / iniciales:* ___
- [ ] Restaurar la red y comprobar que los eventos pendientes se sincronizan sin duplicados. — *Fecha / iniciales:* ___

## 4. Supabase: cloud y modelo de datos

*Hito crítico: un dato real llega desde la Pi a la nube*

- [ ] Crear proyecto Supabase y guardar URL, claves y secretos solo en variables de entorno. — *Fecha / iniciales:* ___
- [ ] Crear tablas: `stations`, `station_events`, `sos_reports`, `device_metrics` y `station_config`. — *Fecha / iniciales:* ___
- [ ] Definir claves, restricciones, timestamps, tipos de evento y reglas de validación. — *Fecha / iniciales:* ___
- [ ] Configurar el riesgo marítimo simulado en `station_config`: Calm, Moderate y High. — *Fecha / iniciales:* ___
- [ ] Hacer que la Raspberry consulte configuración remota y conserve el último valor válido localmente. — *Fecha / iniciales:* ___
- [ ] Insertar el primer evento real desde la Raspberry y verificarlo en Supabase. — *Fecha / iniciales:* ___
- [ ] Aplicar RLS o una API/función de ingesta con credenciales limitadas; nunca subir secretos a GitHub. — *Fecha / iniciales:* ___

## 5. Power BI con datos reales

*El dashboard debe aportar valor y no depender de datos ficticios*

- [ ] Conectar Power BI a Supabase y comprobar que puede actualizar datos reales. — *Fecha / iniciales:* ___
- [ ] Construir `FactEvents` y dimensiones de estación, fecha, fuente y riesgo; documentar relaciones. — *Fecha / iniciales:* ___
- [ ] Crear KPIs: Total Events, Warnings Activated, SOS Events, Warning Rate y Stations Online. — *Fecha / iniciales:* ___
- [ ] Incluir eventos por fuente, alertas por riesgo/hora y tabla de eventos recientes. — *Fecha / iniciales:* ___
- [ ] Añadir estado técnico de la Raspberry: última conexión, CPU, RAM, disco y temperatura. — *Fecha / iniciales:* ___
- [ ] Etiquetar claramente cualquier dato simulado; el flujo principal debe ser real. — *Fecha / iniciales:* ___
- [ ] Pedir a una persona distinta que actualice el dashboard durante la demostración. — *Fecha / iniciales:* ___

## 6. Integraciones para nota alta

*Añadir únicamente cuando el flujo obligatorio sea estable*

- [ ] Implementar MQTT con tópicos documentados y enlazarlo de forma fiable con la ingesta a Supabase. — *Fecha / iniciales:* ___
- [ ] Crear una web móvil de SOS que inserte informes en Supabase con validación de campos. — *Fecha / iniciales:* ___
- [ ] Incluir roles o vistas básicas para que un operador consulte SOS abiertos. — *Fecha / iniciales:* ___
- [ ] Implementar MCP solo sobre datos reales: herramientas de lectura y solicitudes pendientes de aprobación. — *Fecha / iniciales:* ___
- [ ] Crear `audit_log` para cambios de configuración, peticiones MCP y aprobaciones humanas. — *Fecha / iniciales:* ___
- [ ] Si hay margen, añadir una segunda estación sin comprometer la estabilidad de la primera. — *Fecha / iniciales:* ___

## 7. Pruebas, documentación y demo

*Preparar una defensa repetible, no una demo improvisada*

- [ ] Ejecutar y grabar: proximidad con riesgo Calm (sin alerta) y con High (LED/buzzer). — *Fecha / iniciales:* ___
- [ ] Ejecutar y grabar: cambio ambiental con High y SOS físico. — *Fecha / iniciales:* ___
- [ ] Ejecutar y grabar: evento de sensor -> Supabase -> actualización de Power BI. — *Fecha / iniciales:* ___
- [ ] Ejecutar y grabar: caída de red, alerta local, cola offline y recuperación. — *Fecha / iniciales:* ___
- [ ] Redactar instalación, cableado, arquitectura, esquema de base de datos, reglas y troubleshooting. — *Fecha / iniciales:* ___
- [ ] Preparar diapositivas de 10-20 minutos: problema, arquitectura, demo, datos, ética, límites y futuro. — *Fecha / iniciales:* ___
- [ ] Ensayar preguntas: Supabase, datos, seguridad, decisiones de umbral, MQTT, Power BI y límites del prototipo. — *Fecha / iniciales:* ___

---

> **Criterio de salida antes de pasar a ampliaciones:** un evento real creado por la Raspberry debe aparecer en Supabase y, tras actualizar el informe, en Power BI. La alerta local debe continuar funcionando sin red.
