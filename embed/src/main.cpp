#include <Arduino.h>
#include <LittleFS.h>
#include <ESP8266WiFi.h>
#include <DHT_U.h>
#include <DHT.h>
#include <ESP8266HTTPClient.h>
#include <ESP8266WebServer.h>

#define CONFIG_PATH "/conf.txt"

#define ANALOG_PIN A0
#define WATER_HIGH_PIN D7
#define WATER_LOW_PIN D6
#define DHT_PIN D2
#define MULTIPLEXER_PIN_S0 D0
#define MULTIPLEXER_PIN_S1 D1
#define MULTIPLEXER_PIN_S2 D3
#define MOTOR_PIN D5

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

int motorStatus = 0;

float temperature = 0;
float humidity = 0;
float luminosity = 0;
float soil = 0;
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

void handleSensors()
{
  sensors_event_t tem;
  sensors_event_t hum;

  dht.temperature().getEvent(&tem);
  dht.humidity().getEvent(&hum);

  temperature = tem.temperature;
  humidity = hum.relative_humidity;

  multiplexerLdr();
  luminosity = analogRead(ANALOG_PIN);

  multiplexerSoil();
  soil = analogRead(ANALOG_PIN);

  waterHigh = digitalRead(WATER_HIGH_PIN);
  waterLow = digitalRead(WATER_LOW_PIN);

  HTTPClient http;

  http.begin("http://" + server + ":" + port + "/api/data");

  http.addHeader("Content-Type", "application/json");

  int code = http.POST("{\"temperature\": \"" + String(temperature) + "\","
                       "\"humidity\": \"" + String(humidity) + "\","
                       "\"luminosity\": \"" + String(luminosity) + "\","
                       "\"soil\": \"" + String(soil) + "\","
                       "\"waterHigh\": \"" + String(waterHigh) + "\","
                       "\"waterLow\": \"" + String(waterLow) + "\"}");

  http.end();

  //Serial.println("code " + String(code) + " resp " + http.getString());
}

/*void handleMotor()
{
  if( motorStatus == MOTOR_ON )
  {
    digitalWrite(  )
  }
}*/

void setup()
{
  pinMode(ANALOG_PIN, INPUT);
  pinMode(WATER_HIGH_PIN, INPUT);
  pinMode(WATER_LOW_PIN, INPUT);
  pinMode(LED_BUILTIN, OUTPUT);
  pinMode(MULTIPLEXER_PIN_S0, OUTPUT);
  pinMode(MULTIPLEXER_PIN_S1, OUTPUT);
  pinMode(MULTIPLEXER_PIN_S2, OUTPUT);
  pinMode( MOTOR_PIN, OUTPUT );

  serialConfig();

  fsConfig();

  loadConfig();

  dht.begin();

  webServer.on("/", onRoot);
  webServer.on("/form", HTTP_POST, onForm);
  //webServer.on( "/motor", onMotor );

  webServer.begin();

  digitalWrite(LED_BUILTIN, LOW);
}

void loop()
{
  webServer.handleClient();

  handleSensors();

  if( motorStatus == 1 )
  {
    motorStatus = 0;

    digitalWrite( MOTOR_PIN, LOW );
  }

  else
  {
    motorStatus = 1;

    digitalWrite( MOTOR_PIN, HIGH );
  }

  //handleMotor();

  delay(1000);
}