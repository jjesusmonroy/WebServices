package com.example.jjesusmonroy.androidwebservices;

import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jjesusmonroy on 20/03/18.
 */

public class GetJson extends AsyncTask<Void,Void,Void> {

    String data = "";
    String dataParsed="";
    String singleParsed="";

    @Override
    protected Void doInBackground(Void... voids) {
        try{
            URL url = new URL("https://api.coindesk.com/v1/bpi/currentprice.json");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new
                    InputStreamReader(inputStream));
            String line ="";
            while (line!=null){
                line = bufferedReader.readLine();
                data = data+line;
            }
            JSONObject jsonObject = new JSONObject(data);
            JSONObject time = new JSONObject(jsonObject.getString("time"));
            JSONObject info = new JSONObject(jsonObject.getString("bpi"));
            JSONObject usd = new JSONObject(info.getString("USD"));
            JSONObject gbp = new JSONObject(info.getString("GBP"));
            JSONObject eur = new JSONObject(info.getString("EUR"));
            dataParsed = "updated :"+time.getString("updated")+"\n"
                        +"Criptomoneda : "+jsonObject.getString("chartName")+"\n"
                        +"USD :"+usd.getString("rate") +"\n"
                        +"GBP :"+gbp.getString("rate")+"\n"
                        +"EUR :"+eur.getString("rate")+"\n"
                        +"about :"+jsonObject.getString("disclaimer");

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        MainActivity.textView.setText(this.dataParsed);
    }

    private String convertTemp(String kelvinTemp){
        double aux = Double.parseDouble(kelvinTemp);
        return aux-273.15+"";
    }
}
