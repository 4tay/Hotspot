package xyz.a4tay.dev.hotspot;

import android.os.StrictMode;
import android.util.Log;
import com.google.android.gms.maps.GoogleMap;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class DotProtocols
    {
    GoogleMap map;
    OkHttpClient client = new OkHttpClient();

    public void enableStrictMode()
        {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        }

    public JSONObject getDots(String url, Double currentCameraLat, Double currentCameraLng) {
        enableStrictMode();

        Request request = new Request.Builder()
                .url(url + "?lat=" + currentCameraLat + "&lng=" + currentCameraLng)
                .get()
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (IOException e) {
            Log.d(this.getClass().getName(), "Error in IO: " + e.toString());
        } catch (JSONException e) {
            Log.d(this.getClass().getName(), "Error in JSON: " + e.toString());
        }
        return new JSONObject();
    }

    public Response putDot(String url, double lat, double lng, Integer colorCode, String dotID)
        {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"body\":{\"lat\":"+lat+",\"lng\":"+lng+"," +
                "\"locationID\":\""+dotID+"\",\"colorCode\":"+colorCode+",\"hash\":\"this is a test\"}}");
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .addHeader("Content-Type", "application/json")
                .build();

        Response response=null;
        {
        try
            {
            response = client.newCall(request).execute();
            return response;
            } catch (IOException e)
            {
            e.printStackTrace();
            }
        }
        return response;
        }
        public Response putDot(String url, double lat, double lng, Integer colorCode, String dotID, String hash)
        {
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\"body\":{\"lat\":"+lat+",\"lng\":"+lng+"," +
                    "\"locationID\":\""+dotID+"\",\"colorCode\":"+colorCode+",\"hash\":"+hash+"}}");
            Request request = new Request.Builder()
                    .url(url)
                    .put(body)
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response response=null;
            {
                try
                {
                    response = client.newCall(request).execute();
                    return response;
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            return response;
        }
    }
