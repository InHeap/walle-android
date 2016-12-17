package inheap.com.justpay.com.justpay.serverManager;


import com.google.gson.Gson;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by ABHISHEK on 11/12/2016.
 */

public class JSONUtils {

    public static JSONObject getJSONObject(String tag, JSONObject jsonObj) throws JSONException {
        JSONObject jsonObject = jsonObj.getJSONObject(tag);
        return jsonObject;
    }

    public static String getString(String tag, JSONObject jsonObj) throws JSONException {
        return jsonObj.getString(tag);
    }

    public static int getInt(String tag, JSONObject jsonObj) throws JSONException {
        return jsonObj.getInt(tag);
    }

    public static boolean getBoolean(String tag, JSONObject jsonObj) throws JSONException {
        return jsonObj.getBoolean(tag);
    }

    public static String getJsonString(Object src, Type type) {
        Gson gson = new Gson();
        return gson.toJson(src, type);
    }

    public String getSHA256HMACString(String secret, String message) {

        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            String hashWithBase64 = Base64.encodeBase64String(sha256_HMAC.doFinal(message.getBytes()));
            System.out.println(hashWithBase64);
            return hashWithBase64;
        } catch (Exception e) {
            System.out.println("Error");
        }
        return null;
    }
}
