#include <Arduino.h>
#include <LittleFS.h>
#include <ESP8266WiFi.h>
#include <DHT_U.h>
#include <DHT.h>
#include <ESP8266HTTPClient.h>
#include <ESP8266WebServer.h>

#define CONFIG_PATH "/conf.txt"
#define CONFIG_BUTTON D3
#define LDR_PIN A0
#define WATER_HIGH_PIN D7
#define WATER_LOW_PIN D6
#define DHT_PIN D2
#define DHT_TYPE DHT11
#define SERVER_PORT 80
#define DEFAUT_MODE 0
#define CONFIG_MODE 1

DHT_Unified dht(DHT_PIN, DHT_TYPE);

ESP8266WebServer webServer(SERVER_PORT);

//String server;
//String port;

String ssid;
String pass;

int mode = DEFAUT_MODE;

float temperature = 0;
float humidity = 0;
float luminosity = 0;
boolean waterHigh = false;
boolean waterLow = false;

void serialConfig()
{
  Serial.begin(9600);

  Serial.println("\n");
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

void loadConfig()
{
  if (LittleFS.exists(CONFIG_PATH))
  {
    Serial.println("Config file found");

    File conf = LittleFS.open(CONFIG_PATH, "r");

    ssid = conf.readStringUntil(';');
    pass = conf.readStringUntil(';');

    conf.close();

    Serial.println("ssid: " + ssid + " pass: " + pass);
  }

  else
  {
    Serial.println("No config file found");

    mode = CONFIG_MODE;
  }
}

ICACHE_RAM_ATTR
void configButon()
{
  Serial.println("Button Pressed! -> Config Mode");
  mode = CONFIG_MODE;
}

void onForm()
{
  webServer.send(200, "text/html", "Saved! Now reloading...");

  ssid = webServer.arg("ssid");
  pass = webServer.arg("pass");

  Serial.println("Received: ssid:" + ssid + " pass:" + pass);

  File file = LittleFS.open(CONFIG_PATH, "w");

  file.print(ssid + ";" + pass + ";");

  file.close();

  mode = DEFAUT_MODE;
}

void onRoot()
{
  webServer.send(200, "text/html", "<body><form style='display:inline-grid' action='/form' method='post'>ssid:<input type='text' name='ssid'>pass:<input type='text' name='pass'><button>Save</button></form></body>");
}

void handleSensors()
{
  sensors_event_t tem;
  sensors_event_t hum;

  dht.temperature().getEvent(&tem);
  dht.humidity().getEvent(&hum);

  temperature = tem.temperature;
  humidity = hum.relative_humidity;

  luminosity = analogRead(LDR_PIN);

  waterHigh = digitalRead(WATER_HIGH_PIN);
  waterLow = digitalRead(WATER_LOW_PIN);

  HTTPClient http;
  WiFiClient client;

  //http.begin(client, server + ":" + port);

  //int code = http.GET();

  //Serial.println( "code " + String( code ) + " resp " + http.getString() );

  Serial.println("-------------");

  Serial.println("temperature: " + String(temperature) + "\n" +
                 "humidity: " + String(humidity) + "\n" +
                 "luminosity: " + String(luminosity) + "\n" +
                 "water high: " + String(waterHigh) + "\n" +
                 "water low: " + String(waterLow));

  http.end();

  delay(1000);
}

void setup()
{
  pinMode(LDR_PIN, INPUT);
  pinMode(WATER_HIGH_PIN, INPUT);
  pinMode(WATER_LOW_PIN, INPUT);
  pinMode(CONFIG_BUTTON, INPUT);
  pinMode(LED_BUILTIN, OUTPUT);

  attachInterrupt(digitalPinToInterrupt(CONFIG_BUTTON), configButon, FALLING);

  serialConfig();

  fsConfig();

  loadConfig();

  dht.begin();

  webServer.on("/", onRoot);
  webServer.on("/form", HTTP_POST, onForm);

  webServer.begin();

  digitalWrite(LED_BUILTIN, HIGH);
}

void loop()
{
  if (mode == CONFIG_MODE)
  {
    digitalWrite(LED_BUILTIN, LOW);

    WiFi.begin();

    Serial.println("Config Mode -> IP: " + WiFi.softAPIP().toString());

    while (mode == CONFIG_MODE)
    {
      webServer.handleClient();
    }
  }

  else
  {
    digitalWrite(LED_BUILTIN, HIGH);

    WiFi.begin(ssid, pass);

    int i = 0;

    while (mode == DEFAUT_MODE && !WiFi.isConnected())
    {
      Serial.print(".");

      delay(1000);

      if (i >= 10)
      {
        mode = CONFIG_MODE;
      }

      i++;
    }

    if (mode == CONFIG_MODE)
    {
      Serial.println("Timeout on connect to Wifi!");
    }
    else
    {
      Serial.println("Connected!");
    }

    while (mode == DEFAUT_MODE)
    {
      handleSensors();
    }
  }
}