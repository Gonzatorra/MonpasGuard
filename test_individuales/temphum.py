import time
from seeed_dht import DHT

TEMPHUM_PIN = 5
SENSOR_TYPE = "11"


def create_sensor():
    """Creates and returns the temperature and humidity sensor."""
    return DHT(SENSOR_TYPE, TEMPHUM_PIN)


def read_environment(sensor):
    """Returns humidity and temperature readings."""
    humidity, temperature = sensor.read()
    return humidity, temperature


def is_valid_reading(humidity, temperature):
    """Checks whether the sensor returned valid readings."""
    return humidity is not None and temperature is not None


def show_reading(humidity, temperature):
    """Displays the temperature and humidity readings."""
    print(f"Temperature: {temperature:.1f} °C | Humidity: {humidity:.1f} %")


def main():
    sensor = create_sensor()

    try:
        while True:
            humidity, temperature = read_environment(sensor)

            if is_valid_reading(humidity, temperature):
                show_reading(humidity, temperature)
            else:
                print("Unable to read the sensor.")

            time.sleep(2)

    except KeyboardInterrupt:
        print("\nProgram stopped.")


if __name__ == "__main__":
    main()
