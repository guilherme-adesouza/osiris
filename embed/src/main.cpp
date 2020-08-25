#include <Arduino.h>
#include <LittleFS.h>
#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>
#include <DHT_U.h>
#include <DHT.h>

#define CONF_PATH "/conf.txt"
#define DHT_PIN D2
#define DHT_TYPE DHT11
#define LDR_PIN A0
#define SERVER_PORT 80

DHT_Unified dht(DHT_PIN, DHT_TYPE);
ESP8266WebServer server(SERVER_PORT);

float temperature = 0;
float humidity = 0;
float luminosity = 0;

boolean led = true;

void onRoot()
{
  server.send(200, "text/json", "{t:" + String(temperature) + ",h:" + String(humidity) + ",l:" + String(luminosity) + "}");
}

void onLed()
{
  if (led)
  {
    digitalWrite(LED_BUILTIN, LOW);

    server.send(200, "text/html", "led on");
  }

  else
  {
    digitalWrite(LED_BUILTIN, HIGH);

    server.send(200, "text/html", "led off");
  }

  led = !led;
}

void fsConfig()
{
  if (LittleFS.begin())
  {
    Serial.println("File System Started");
  }

  else
  {
    Serial.println("File System Error");
  }
}

void wifiConfig(String ssid, String pass)
{
  WiFi.begin(ssid, pass);

  int i = 0;

  while (WiFi.status() != WL_CONNECTED && i < 10)
  {
    Serial.print(".");

    i++;

    delay(1000);
  }

  Serial.print("\n");

  if (WiFi.isConnected())
  {
    Serial.println("Conected to " + ssid + "!: " + WiFi.localIP().toString());
  }

  else
  {
    Serial.println("Failed to connect to: " + ssid);
  }
}

void serverConfig()
{
  server.on("/", HTTP_GET, onRoot);

  server.on("/led", HTTP_GET, onLed);

  server.begin();

  Serial.println("Server Started!");
}

void loadConfig()
{
  if (LittleFS.exists(CONF_PATH))
  {
    Serial.println("Configuration File Found!");

    File conf = LittleFS.open(CONF_PATH, "r");

    String ssid = conf.readStringUntil(';');
    String pass = conf.readStringUntil(';');

    conf.close();

    wifiConfig(ssid, pass);
  }

  else
  {
    Serial.println("Configuration File not Found!");
  }
}

void setup()
{
  pinMode(LDR_PIN, INPUT);

  pinMode(LED_BUILTIN, OUTPUT);

  Serial.begin(9600);

  Serial.println("\n");

  fsConfig();

  loadConfig();

  serverConfig();

  dht.begin();
}

void loop()
{
  sensors_event_t tem;
  sensors_event_t hum;

  dht.temperature().getEvent(&tem);
  dht.humidity().getEvent(&hum);

  temperature = tem.temperature;
  humidity = hum.relative_humidity;

  luminosity = analogRead(LDR_PIN);

  server.handleClient();

  delay(10);
}