import RPi.GPIO as GPIO
from time import sleep

BUZZER_PIN = 22


def sonar_buzzer(segundos=2):
    print("Buzzer encendido")
    GPIO.output(BUZZER_PIN, GPIO.HIGH)
    sleep(segundos)
    GPIO.output(BUZZER_PIN, GPIO.LOW)
    print("Buzzer apagado")


GPIO.setmode(GPIO.BCM)
GPIO.setup(BUZZER_PIN, GPIO.OUT)

try:
    sonar_buzzer()

except KeyboardInterrupt:
    print("\nPrograma detenido.")

finally:
    GPIO.output(BUZZER_PIN, GPIO.LOW)
    GPIO.cleanup()
