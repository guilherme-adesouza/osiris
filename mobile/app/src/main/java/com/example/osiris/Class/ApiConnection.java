package com.example.osiris.Class;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.util.EntityUtils;

public class ApiConnection {

    private final static String URL             = "http://192.168.0.100:8080/api/";
    public final static String TABLE_ACTION     = "action";
    public final static String TABLE_DATA       = "data";
    public final static String TABLE_DEVICE     = "device";
    public final static String TABLE_SCHEDULE   = "schedule";
    public final static String TABLE_USER       = "user";

    public static JSONObject makePost(String from, JSONObject jsonData) {
        return make(from, null, jsonData, "POST");
    }

    public static JSONObject makePut(String from, String indice, JSONObject jsonData) {
        return make(from, indice, jsonData, "PUT");
    }

    public static JSONObject makeDelete(String from, String indice) {
        return make(from, indice, new JSONObject(), "DELETE");
    }

    public static JSONArray makeGet(String[] parametersFixed, String from) {
        StringBuilder result = new StringBuilder();
        JSONArray json = null;
        //Montar a URL
        String dataUrlTemp = URL + from;
        if (parametersFixed != null) {
            for (int i = 0; i < parametersFixed.length; i++) {
                dataUrlTemp += "/" + parametersFixed[i];
            }
        }

        Log.i("URL DO GET => ", dataUrlTemp);
        try {
            //Estabelecer a conexão
            URL url = new URL(dataUrlTemp);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            //Receber o resultado
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            //Montar o JSON
            String st_json = result.toString();
            json = new JSONArray(st_json);
            Log.i("RETORNO BRUTO GET => ", st_json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return json;
        }

    }

    public static JSONObject makeGetId(String[] parametersFixed, String from) {
        StringBuilder result = new StringBuilder();
        JSONObject json = null;
        //Montar a URL
        String dataUrlTemp = URL + from;
        if (parametersFixed != null) {
            for (int i = 0; i < parametersFixed.length; i++) {
                dataUrlTemp += "/" + parametersFixed[i];
            }
        }

        Log.i("URL DO GET => ", dataUrlTemp);
        try {
            //Estabelecer a conexão
            URL url = new URL(dataUrlTemp);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            //Receber o resultado
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            //Montar o JSON
            String st_json = result.toString();
            json = new JSONObject(st_json);
            Log.i("RETORNO BRUTO GET => ", st_json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return json;
        }

    }

    private static JSONObject make (String from, String indice, JSONObject jsonData, String method){
        URL myUrl;
        HttpURLConnection connection = null;
        JSONObject json = null;

        //Inicio: Formando a URL
        String urlTemp = URL + from;
        if (indice != null) {
            urlTemp += "/" + indice;
        }
        Log.i("URL DO POST => ", urlTemp);
        //Fim: Formando a URL

        try {
            //Estabelecer a conexão
            myUrl = new URL(urlTemp);
            connection = (HttpURLConnection) myUrl.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod(method); // hear you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
            connection.connect();
            //Enviar as requisições
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(jsonData.toString());
            wr.flush();
            wr.close();
            //Receber os resultados
            InputStream is;
            String response = connection.getResponseMessage();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseOutput = new StringBuilder();
            String line = "";
            while ((line = br.readLine()) != null) {
                responseOutput.append(line);
            }
            br.close();
            Log.i("RETORNO BRUTO "+ method +" => ", responseOutput.toString());
            //Montar o JSON
            //json = new JSONObject(responseOutput.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            return json;
        }
    }
}
