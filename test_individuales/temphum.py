import time
from seeed_dht import DHT

TEMPHUM_PIN = 5
sensor = DHT('11', TEMPHUM_PIN)

try:
    while True:
        humi, temp = sensor.read()

        if humi is not None and temp is not None:
            print(f"Temperatura: {temp:.1f} °C | Humedad: {humi:.1f} %")
        else:
            print("No se ha podido leer el sensor.")

        time.sleep(2)

except KeyboardInterrupt:
    print("\nPrograma detenido.")
