#include <Arduino.h>
#include <LittleFS.h>
#include <ESP8266WiFi.h>
#include <DHT_U.h>
#include <DHT.h>

#define CONF_PATH "/conf.txt"
#define DHT_PIN D2
#define DHT_TYPE DHT11

String ssid;
String pass;

DHT_Unified dht( DHT_PIN, DHT_TYPE );

void fsConfig()
{
  if( LittleFS.begin() )
  {
    Serial.println( "File System Started" );
  }

  else
  {
    Serial.println( "File System Error" );
  }
}

void wifiConfig()
{
  WiFi.begin( ssid, pass );

  int i = 0;

  while( WiFi.status() != WL_CONNECTED && i < 10 )
  {
    Serial.print( "." );
    
    i++;
    
    delay( 100 );
  }

  if( WiFi.isConnected() )
  {
    Serial.println( "\nConected to " + ssid + "!: " + WiFi.localIP().toString() );
  }

  else
  {
    Serial.println( "\nFailed to connect to: " + ssid );
  }
} 

void loadConfig()
{
  if( LittleFS.exists( CONF_PATH ) )
  {
    Serial.println( "Configuration File Found!" );

    File conf = LittleFS.open( CONF_PATH, "r" );

    ssid = conf.readStringUntil( ';' );
    pass = conf.readStringUntil( ';' );

    conf.close();
  }

  else
  {
    Serial.println( "Configuration File not Found!" );
  }
}

void setup() {
  Serial.begin( 9600 );
  
  Serial.println( "\n" );

  fsConfig();

  loadConfig();

  wifiConfig();

  dht.begin();
}

void loop() {

  sensors_event_t tem;
  sensors_event_t hum;

  dht.temperature().getEvent( &tem );
  dht.humidity().getEvent( &hum );

  float t = tem.temperature;
  float h = hum.relative_humidity;

  Serial.printf( "temperature: %.2f\n", t );
  Serial.printf( "humidity: %.2f\n", h );

  delay( 1000 );
}