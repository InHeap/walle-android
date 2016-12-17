package inheap.com.justpay.com.justpay.serverManager;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by ABHISHEK on 11/12/2016.
 */

public class JustPayHTTPRequest {

    private static final String TAG = "JUSTPAY_REQUEST";

    // ADD PARAM IN HTTP BODY IN POST REQUEST
    public static HttpURLConnection doPOSTRequest(String urlString, String httpBody) {

        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) (new URL(urlString).openConnection());
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setInstanceFollowRedirects(true);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            if(!httpBody.isEmpty()) {
                Writer writer = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8"));
                writer.write(httpBody);
                writer.flush();
                writer.close();
            }

            httpURLConnection.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return httpURLConnection;
    }

    // IN GET REQUEST APPEND PARAM IN URL STRING
    public static HttpURLConnection doGETRequest(String urlString, String params) {

        HttpURLConnection httpURLConnection = null;
        String URLString = params.isEmpty() ? urlString : (urlString + "?" + params);
        try {
            httpURLConnection = (HttpURLConnection) (new URL(URLString).openConnection());
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setInstanceFollowRedirects(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return httpURLConnection;

    }
}
