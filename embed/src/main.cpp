#include <Arduino.h>
#include <LittleFS.h>
#include <ESP8266WiFi.h>
#include <DHT_U.h>
#include <DHT.h>
#include <ESP8266HTTPClient.h>
#include <ESP8266WebServer.h>

#define CONFIG_PATH "/conf.txt"

#define ANALOG_PIN A0
#define DHT_PIN D2
#define MULTIPLEXER_PIN_S0 D0
#define MULTIPLEXER_PIN_S1 D1
#define MULTIPLEXER_PIN_S2 D3
#define MOTOR_PIN D6
#define BUTTON_PIN D7

#define MOTOR_OFF 0
#define MOTOR_ON 1

#define DHT_TYPE DHT11

#define SERVER_PORT 80

DHT_Unified dht(DHT_PIN, DHT_TYPE);

ESP8266WebServer webServer(SERVER_PORT);

String server = "192.168.0.100";
String port = "8080";

String ssid;
String pass;

int motorStatus = MOTOR_OFF;

float temperature = 0;
float humidity = 0;
float luminosity = 0;
float soil = 0;
float waterHigh = 0;
float waterLow = 0;

int dataTimer = 0;

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

void wifiConfig()
{
  WiFi.begin(ssid, pass);

  int i = 0;

  while (!WiFi.isConnected() && i < 10)
  {
    Serial.print(".");

    delay(1000);

    i++;
  }

  if (WiFi.isConnected())
  {
    Serial.println("Wifi conected to: " + ssid);
  }

  else
  {
    Serial.println("Connection Error!");
  }
}

void loadConfig()
{
  if (LittleFS.exists(CONFIG_PATH))
  {
    Serial.println("Configuration file found");

    File conf = LittleFS.open(CONFIG_PATH, "r");

    ssid = conf.readStringUntil(';');
    pass = conf.readStringUntil(';');

    conf.close();

    Serial.println("ssid: " + ssid + " pass: " + pass);
  }

  else
  {
    Serial.println("No configuration file found");
  }
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

  wifiConfig();
}

void onRoot()
{
  webServer.send(200, "text/html", "<body><form style='display:inline-grid' action='/form' method='post'>ssid:<input type='text' name='ssid'>pass:<input type='text' name='pass'><button>Save</button></form></body>");
}

void onMotorON()
{
  motorStatus = MOTOR_ON;

  webServer.send(200, "text/html", "on");
}

void onMotorOFF()
{
  motorStatus = MOTOR_OFF;

  webServer.send(200, "text/html", "off");
}

ICACHE_RAM_ATTR
void onButton()
{
  Serial.println("button!");

  switch (motorStatus)
  {
  case MOTOR_OFF:
    motorStatus = MOTOR_ON;
    break;

  case MOTOR_ON:
    motorStatus = MOTOR_OFF;
    break;
  }
}

void multiplexerLdr()
{
  digitalWrite(MULTIPLEXER_PIN_S0, HIGH);
  digitalWrite(MULTIPLEXER_PIN_S1, LOW);
  digitalWrite(MULTIPLEXER_PIN_S2, LOW);
}

void multiplexerSoil()
{
  digitalWrite(MULTIPLEXER_PIN_S0, LOW);
  digitalWrite(MULTIPLEXER_PIN_S1, LOW);
  digitalWrite(MULTIPLEXER_PIN_S2, LOW);
}

void multiplexerWaterHigh()
{
  digitalWrite(MULTIPLEXER_PIN_S0, LOW);
  digitalWrite(MULTIPLEXER_PIN_S1, HIGH);
  digitalWrite(MULTIPLEXER_PIN_S2, LOW);
}

void multiplexerWaterLow()
{
  digitalWrite(MULTIPLEXER_PIN_S0, HIGH);
  digitalWrite(MULTIPLEXER_PIN_S1, HIGH);
  digitalWrite(MULTIPLEXER_PIN_S2, LOW);
}

void handleSensors()
{
  sensors_event_t tem;
  sensors_event_t hum;

  dht.temperature().getEvent(&tem);
  dht.humidity().getEvent(&hum);

  temperature = tem.temperature;
  humidity = hum.relative_humidity;

  multiplexerLdr();
  delay(2);
  luminosity = analogRead(ANALOG_PIN);

  multiplexerSoil();
  delay(2);
  soil = analogRead(ANALOG_PIN);

  multiplexerWaterHigh();
  delay(2);
  waterHigh = analogRead(ANALOG_PIN);

  multiplexerWaterLow();
  delay(2);
  waterLow = analogRead(ANALOG_PIN);

  HTTPClient http;

  http.begin("http://" + server + ":" + port + "/api/data");

  http.addHeader("Content-Type", "application/json");

  String data = "{\"temperature\": \"" + String(temperature) + "\","
                                                               "\"humidity\": \"" +
                String(humidity) + "\","
                                   "\"luminosity\": \"" +
                String(luminosity) + "\","
                                     "\"soil\": \"" +
                String(soil) + "\","
                               "\"waterHigh\": \"" +
                String(waterHigh) + "\","
                                    "\"waterLow\": \"" +
                String(waterLow) + "\"}";

  http.end();

  Serial.println(data);

  //Serial.println("code " + String(code) + " resp " + http.getString());
}

void handleMotor()
{
  switch (motorStatus)
  {
  case MOTOR_OFF:
    digitalWrite(MOTOR_PIN, LOW);
    break;

  case MOTOR_ON:
    digitalWrite(MOTOR_PIN, HIGH);
    break;
  }
}

void setup()
{
  pinMode(ANALOG_PIN, INPUT);
  pinMode(BUTTON_PIN, INPUT);
  pinMode(MULTIPLEXER_PIN_S0, OUTPUT);
  pinMode(MULTIPLEXER_PIN_S1, OUTPUT);
  pinMode(MULTIPLEXER_PIN_S2, OUTPUT);
  pinMode(MOTOR_PIN, OUTPUT);

  attachInterrupt(digitalPinToInterrupt(BUTTON_PIN), onButton, FALLING);

  serialConfig();

  fsConfig();

  loadConfig();

  wifiConfig();

  dht.begin();

  webServer.on("/", onRoot);
  webServer.on("/form", HTTP_POST, onForm);
  webServer.on("/motorON", onMotorON);
  webServer.on("/motorOFF", onMotorOFF);

  webServer.begin();
}

void loop()
{
  if (dataTimer >= 5000)
  {
    handleSensors();

    dataTimer = 0;
  }

  webServer.handleClient();

  handleMotor();

  dataTimer++;

  delay(1);
}