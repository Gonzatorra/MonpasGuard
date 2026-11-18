import RPi.GPIO as GPIO
from time import sleep

BUTTON_PIN = 25   # GPIO de la señal del botón
LED_PIN = 24      # GPIO de la señal del LED del botón


def pulsar():
    print("Pulsado")

    # Confirmación visual del SOS
    GPIO.output(LED_PIN, GPIO.HIGH)
    sleep(1)
    GPIO.output(LED_PIN, GPIO.LOW)


GPIO.setmode(GPIO.BCM)

# Botón activo en LOW: al pulsarlo conecta con GND
GPIO.setup(BUTTON_PIN, GPIO.IN, pull_up_down=GPIO.PUD_UP)

# LED del botón
GPIO.setup(LED_PIN, GPIO.OUT)
GPIO.output(LED_PIN, GPIO.LOW)

print("Esperando pulsación...")

try:
    while True:
        if GPIO.input(BUTTON_PIN) == GPIO.LOW:
            pulsar()

            # Esperar a que se suelte para no detectar varias pulsaciones
            while GPIO.input(BUTTON_PIN) == GPIO.LOW:
                sleep(0.05)

        sleep(0.05)

except KeyboardInterrupt:
    print("\nPrograma detenido.")

finally:
    GPIO.output(LED_PIN, GPIO.LOW)
    GPIO.cleanup()
