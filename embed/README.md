# Embed sistem documentation :godmode:

## **Board used: NODEMCU ESP8266**

## Wifi configuration:

To configure a Wifi connection edit the conf.txt file on the data directory, 
the first parameter should be the ssid of the network and the second the password,
then the file needs to be uploaded to the flash memory of the board,
every time the board is turned on it checks for the configuration file and reads the paramethers.

## mDNS:

The default mDNS domain is **osiris.local**, this should be used to easily find the server on the network,
but some networks may not suport this feature.

## Routes:

[GET]
/api/sensors/ -> Returns a JSON containing all the sensor data

[POST]
/api/water/on/ -> Turns the water pump ON
/api/water/off/ -> Turns the water pump OFF