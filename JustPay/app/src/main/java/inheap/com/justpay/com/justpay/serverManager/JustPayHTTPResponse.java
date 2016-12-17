package inheap.com.justpay.com.justpay.serverManager;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by ABHISHEK on 11/12/2016.
 */

public class JustPayHTTPResponse {

    private static final String TAG = "JUSTPAY_RESPONSE";

    public static JSONObject responseForRequest(HttpURLConnection urlConnection)
    {
        InputStream inputStream = null;
        StringBuffer stringBuffer = new StringBuffer();

        try {
            Log.i(TAG,"RESPONSE :: " + urlConnection.getResponseCode());
            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) { // 200
                inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line + "\r\n");
                }

                inputStream.close();
                urlConnection.disconnect();
                Log.i(TAG,"RESPONSE STRING :: " + stringBuffer.toString());
                return new JSONObject(stringBuffer.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
