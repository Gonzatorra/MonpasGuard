import RPi.GPIO as GPIO
from time import sleep

BUTTON_PIN = 25
LED_PIN = 24


def setup_button_led():
    """Configures the SOS button and its confirmation LED."""
    GPIO.setup(BUTTON_PIN, GPIO.IN, pull_up_down=GPIO.PUD_UP)
    GPIO.setup(LED_PIN, GPIO.OUT)
    turn_led_off()


def is_button_pressed():
    """Returns True when the SOS button is pressed."""
    return GPIO.input(BUTTON_PIN) == GPIO.LOW


def turn_led_on():
    """Turns the button LED on."""
    GPIO.output(LED_PIN, GPIO.HIGH)


def turn_led_off():
    """Turns the button LED off."""
    GPIO.output(LED_PIN, GPIO.LOW)


def confirm_sos():
    """Displays local visual confirmation after an SOS button press."""
    print("SOS button pressed")
    turn_led_on()
    sleep(1)
    turn_led_off()


def wait_until_released():
    """Waits until the user releases the button."""
    while is_button_pressed():
        sleep(0.05)


def main():
    GPIO.setmode(GPIO.BCM)
    setup_button_led()

    print("Waiting for SOS button press...")

    try:
        while True:
            if is_button_pressed():
                confirm_sos()
                wait_until_released()

            sleep(0.05)

    except KeyboardInterrupt:
        print("\nProgram stopped.")

    finally:
        turn_led_off()
        GPIO.cleanup()


if __name__ == "__main__":
    main()
