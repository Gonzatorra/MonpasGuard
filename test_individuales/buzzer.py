import RPi.GPIO as GPIO
from time import sleep

BUZZER_PIN = 22


def setup_buzzer():
    """Configures the buzzer GPIO pin."""
    GPIO.setup(BUZZER_PIN, GPIO.OUT)
    turn_buzzer_off()


def turn_buzzer_on():
    """Turns the buzzer on."""
    GPIO.output(BUZZER_PIN, GPIO.HIGH)


def turn_buzzer_off():
    """Turns the buzzer off."""
    GPIO.output(BUZZER_PIN, GPIO.LOW)


def sound_buzzer(seconds=2):
    """Turns the buzzer on for a given number of seconds."""
    print("Buzzer on")
    turn_buzzer_on()
    sleep(seconds)
    turn_buzzer_off()
    print("Buzzer off")


def main():
    GPIO.setmode(GPIO.BCM)
    setup_buzzer()

    try:
        sound_buzzer()

    except KeyboardInterrupt:
        print("\nProgram stopped.")

    finally:
        turn_buzzer_off()
        GPIO.cleanup()


if __name__ == "__main__":
    main()
