package com.dev.todos.Instagram;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;




import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;



public class RequestInstagramAPI extends AsyncTask<Void, String, String> {
    String token;
    public RequestInstagramAPI(String token){
        this.token=token;

    }

    @Override
    protected String doInBackground(Void... params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("https://api.instagram.com/v1/users/self/?access_token="+ token);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            return EntityUtils.toString(httpEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String response) {

        if (response != null) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject jsonData = jsonObject.getJSONObject("data");
                if (jsonData.has("id")) {
                    Log.d("TAG", "onPostExecute: "+jsonData.getString("id")+"---"+jsonData.getString("username")+"----"+jsonData.getString("profile_picture"));
                   }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
           /* Toast toast = Toast.makeText(getApplicationContext(),"Login error!", Toast.LENGTH_LONG);
            toast.show();*/
        }
    }
}