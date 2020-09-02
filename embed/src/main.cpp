#include <Arduino.h>
#include <LittleFS.h>
#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>
#include <ESP8266mDNS.h>
#include <DHT_U.h>
#include <DHT.h>

#define CONF_PATH "/conf.txt"
#define MDNS_DOMAIN "osiris"
#define LDR_PIN A0
#define WATER_HIGH_PIN D7
#define WATER_LOW_PIN D6
#define DHT_PIN D2
#define DHT_TYPE DHT11
#define SERVER_PORT 80

DHT_Unified dht(DHT_PIN, DHT_TYPE);
ESP8266WebServer server(SERVER_PORT);

float temperature = 0;
float humidity = 0;
float luminosity = 0;
boolean waterHigh = false;
boolean waterLow = false;

void onSensors()
{
  server.send
  (
    200, 
    "application/json", 
    "{ \"temperature\":" + String(temperature) + "," +
      "\"humidity\":"    + String(humidity)    + "," +
      "\"luminosity\":"  + String(luminosity)  + "," +
      "\"waterHigh\":"   + String(waterHigh)   + "," +
      "\"waterLow\":"    + String(waterLow)    + " }"
    );
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
  WiFi.mode( WIFI_STA );
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
  server.on("/api/sensors", HTTP_GET, onSensors);

  server.begin();

  Serial.println("Server Started!");

  if( MDNS.begin( MDNS_DOMAIN ) )
  {
    MDNS.addService("http", "tcp", 80);

    Serial.println( "MDNS Started: " + String( MDNS_DOMAIN ) );
  }

  else
  {
    Serial.println( "MDNS error" );
  }
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
  pinMode( WATER_HIGH_PIN, INPUT );
  pinMode( WATER_LOW_PIN, INPUT );

  pinMode(LED_BUILTIN, OUTPUT);
  
  Serial.begin(9600);

  Serial.println("\n");

  fsConfig();

  loadConfig();

  serverConfig();

  dht.begin();

  digitalWrite( LED_BUILTIN, HIGH );
}

void loop()
{
  MDNS.update();

  server.handleClient();

  sensors_event_t tem;
  sensors_event_t hum;

  dht.temperature().getEvent(&tem);
  dht.humidity().getEvent(&hum);

  temperature = tem.temperature;
  humidity = hum.relative_humidity;

  luminosity = analogRead(LDR_PIN);

  waterHigh = digitalRead( WATER_HIGH_PIN );
  waterLow = digitalRead( WATER_LOW_PIN );
  
  delay(10);
}