import asyncio
import json
import os
from contextlib import asynccontextmanager

import aiomqtt
from dotenv import load_dotenv
from fastapi import FastAPI, HTTPException
from supabase import create_client

load_dotenv()

supabase = create_client(os.environ["SUPABASE_URL"], os.environ["SUPABASE_KEY"])

MQTT_HOST = os.getenv("MQTT_HOST", "localhost")
MQTT_PORT = int(os.getenv("MQTT_PORT", "1883"))
MQTT_USER = os.getenv("MQTT_USER") or None
MQTT_PASSWORD = os.getenv("MQTT_PASSWORD") or None
MQTT_TOPIC = os.getenv("MQTT_TOPIC", "sensores/#")
TABLA = "lecturas"


def guardar_lectura(dispositivo: str, datos: dict):
    supabase.table(TABLA).insert({"dispositivo": dispositivo, "datos": datos}).execute()


async def escuchar_mqtt():
    """Se suscribe al broker y guarda cada mensaje en Supabase. Reconecta si se cae."""
    while True:
        try:
            async with aiomqtt.Client(
                MQTT_HOST, MQTT_PORT, username=MQTT_USER, password=MQTT_PASSWORD
            ) as client:
                await client.subscribe(MQTT_TOPIC)
                print(f"Suscrito a {MQTT_TOPIC}")
                async for msg in client.messages:
                    try:
                        datos = json.loads(msg.payload)
                    except (json.JSONDecodeError, UnicodeDecodeError):
                        print("Mensaje no válido, ignorado")
                        continue
                    # topic "sensores/raspberry" -> dispositivo "raspberry"
                    dispositivo = str(msg.topic).split("/")[-1]
                    await asyncio.to_thread(guardar_lectura, dispositivo, datos)
        except aiomqtt.MqttError as e:
            print(f"MQTT desconectado ({e}). Reintentando en 5 s...")
            await asyncio.sleep(5)


@asynccontextmanager
async def lifespan(app: FastAPI):
    tarea = asyncio.create_task(escuchar_mqtt())
    yield
    tarea.cancel()


app = FastAPI(title="API Sensores", lifespan=lifespan)


@app.get("/")
def estado():
    return {"ok": True}


@app.get("/lecturas")
def listar_lecturas(limite: int = 50):
    res = (
        supabase.table(TABLA)
        .select("*")
        .order("created_at", desc=True)
        .limit(limite)
        .execute()
    )
    return res.data


@app.get("/lecturas/ultima")
def ultima_lectura():
    res = (
        supabase.table(TABLA)
        .select("*")
        .order("created_at", desc=True)
        .limit(1)
        .execute()
    )
    if not res.data:
        raise HTTPException(status_code=404, detail="Sin lecturas")
    return res.data[0]