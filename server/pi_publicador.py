# Script para la Raspberry Pi: publica datos por MQTT cada 5 segundos.
# Instalar en la Pi:  pip install paho-mqtt
import json
import random
import time

import paho.mqtt.client as mqtt

BROKER = "IP_DEL_BROKER"  # p. ej. "192.168.1.50" o "localhost" si el broker está en la Pi
PUERTO = 1883
TOPIC = "sensores/raspberry"

client = mqtt.Client(mqtt.CallbackAPIVersion.VERSION2)
# client.username_pw_set("usuario", "contraseña")  # si tu broker lo pide
client.connect(BROKER, PUERTO)
client.loop_start()

while True:
    # Sustituye esto por la lectura real de tus sensores
    datos = {"temperatura": round(random.uniform(18, 30), 1), "humedad": random.randint(30, 70)}
    client.publish(TOPIC, json.dumps(datos), qos=1)
    print("Enviado:", datos)
    time.sleep(5)