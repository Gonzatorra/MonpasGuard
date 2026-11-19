import time
from grove.grove_ultrasonic_ranger import GroveUltrasonicRanger

ULTRASONIC_PIN = 16


def create_sensor():
    """Creates and returns the ultrasonic sensor."""
    return GroveUltrasonicRanger(ULTRASONIC_PIN)


def read_distance(sensor):
    """Returns the measured distance in centimetres."""
    return sensor.get_distance()


def show_distance(distance):
    """Displays the measured distance."""
    print(f"Distance: {distance:.1f} cm")


def main():
    sensor = create_sensor()

    try:
        while True:
            distance = read_distance(sensor)
            show_distance(distance)
            time.sleep(1)

    except KeyboardInterrupt:
        print("\nProgram stopped.")


if __name__ == "__main__":
    main()
