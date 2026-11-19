import RPi.GPIO as GPIO
from time import sleep

BUTTON_PIN = 25
LED_PIN = 24
BUZZER_PIN = 22


def setup_components():
    """Configures the SOS button, LED and buzzer."""
    GPIO.setup(BUTTON_PIN, GPIO.IN, pull_up_down=GPIO.PUD_UP)

    GPIO.setup(LED_PIN, GPIO.OUT)
    GPIO.setup(BUZZER_PIN, GPIO.OUT)

    turn_led_off()
    turn_buzzer_off()


def is_button_pressed():
    """Returns True when the SOS button is pressed."""
    return GPIO.input(BUTTON_PIN) == GPIO.LOW


def turn_led_on():
    """Turns the SOS confirmation LED on."""
    GPIO.output(LED_PIN, GPIO.HIGH)


def turn_led_off():
    """Turns the SOS confirmation LED off."""
    GPIO.output(LED_PIN, GPIO.LOW)


def turn_buzzer_on():
    """Turns the buzzer on."""
    GPIO.output(BUZZER_PIN, GPIO.HIGH)


def turn_buzzer_off():
    """Turns the buzzer off."""
    GPIO.output(BUZZER_PIN, GPIO.LOW)


def activate_sos_alert(seconds=2):
    """Activates the local visual and sound SOS alert."""
    print("SOS button pressed - local alert activated")

    turn_led_on()
    turn_buzzer_on()

    sleep(seconds)

    turn_led_off()
    turn_buzzer_off()


def wait_until_released():
    """Waits until the user releases the SOS button."""
    while is_button_pressed():
        sleep(0.05)


def main():
    GPIO.setmode(GPIO.BCM)
    setup_components()

    print("Waiting for SOS button press...")

    try:
        while True:
            if is_button_pressed():
                activate_sos_alert()
                wait_until_released()

            sleep(0.05)

    except KeyboardInterrupt:
        print("\nProgram stopped.")

    finally:
        turn_led_off()
        turn_buzzer_off()
        GPIO.cleanup()


if __name__ == "__main__":
    main()
