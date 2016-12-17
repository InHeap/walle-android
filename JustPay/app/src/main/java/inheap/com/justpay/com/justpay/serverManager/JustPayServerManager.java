package inheap.com.justpay.com.justpay.serverManager;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import inheap.com.justpay.com.justpay.user.JustPayUser;

/**
 * Created by ABHISHEK on 11/12/2016.
 */

public class JustPayServerManager extends AsyncTask<Object, Void, Void> {

    public interface CompletionListener {

        public void loginListener(String tag, Object[] array);

        public void registrationListener(String tag, Object[] array);

        public void paymentListener(String tag, Object[] array);

        public void userUpdateListener(String tag, Object[] array);

        public void userInfoListener(String tag, Object[] array);

        public void userCheckListener(String json, Boolean flag);
    }

    //    private static final String BASE_URL = "www.justpay.com";
    private static final String BASE_URL = "http://192.168.0.108:3003";
    private static final String REGISTERATION_URL = "/auth/register";
    private static final String LOGIN_URL = "/auth/login";
    private static final String CHECK_USER_URL = "/auth/check";
    private static final String USER_INFO_URL = "/user";
    private static final String USER_UPDATE_URL = "/user";
    private static final String USER_DELETE_URL = "/user";
    private static final String LOGOUT_URL = "/auth/logout";
    private static final String PAYMENT_URL = "/pay";

    private CompletionListener listener;

    public JustPayServerManager() {
    }

    public JustPayServerManager(CompletionListener listener) {
        this.listener = listener;
    }

    public void userRegistration(JustPayUser justPayUser) {

        String urlString = BASE_URL + REGISTERATION_URL;
        Gson gson = new Gson();
        String httpBody = gson.toJson(justPayUser, JustPayUser.class);

        HttpURLConnection urlConnection = JustPayHTTPRequest.doPOSTRequest(urlString, httpBody);
        JSONObject jsonResponse = JustPayHTTPResponse.responseForRequest(urlConnection);
        if (jsonResponse != null) {
            // In this there is nothing in response
            Log.v("REG_TAG : ", jsonResponse.toString());
        }
    }

    public void userCheck(String userName) {

        String urlString = BASE_URL + CHECK_USER_URL;
        String params = "userName=" + userName;
        HttpURLConnection urlConnection = JustPayHTTPRequest.doGETRequest(urlString, params);
        JSONObject jsonResponse = JustPayHTTPResponse.responseForRequest(urlConnection);
        if (jsonResponse != null) {
            try {
                Boolean check = JSONUtils.getBoolean("available", jsonResponse);
                listener.userCheckListener(jsonResponse.toString(), check);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void userLogout() {

        String urlString = BASE_URL + LOGOUT_URL;
        HttpURLConnection urlConnection = JustPayHTTPRequest.doGETRequest(urlString, "");
        JSONObject jsonResponse = JustPayHTTPResponse.responseForRequest(urlConnection);
        if (jsonResponse != null) {
            // Response : true / false
            Boolean check = null;
            try {
                check = JSONUtils.getBoolean("available", jsonResponse);
                listener.userCheckListener(jsonResponse.toString(), check);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public void userLogin(String userName, String password) {

        String urlString = BASE_URL + LOGIN_URL;

        Map<String, String> map = new HashMap<String, String>();
        map.put("userName", userName);
        map.put("password", password);
        map.put("platform", "ANDROID");

        Gson gson = new Gson();
        String httpBody = gson.toJson(map, HashMap.class);
        HttpURLConnection urlConnection = JustPayHTTPRequest.doPOSTRequest(urlString, httpBody);
        JSONObject jsonResponse = JustPayHTTPResponse.responseForRequest(urlConnection);
        if (jsonResponse != null) {
            // Response : true / false
        }
    }

    public void makePaymentForReceiver(String senderUserName, String senderDeviceId, int amount, String token, String title, String description) {

        String urlString = BASE_URL + PAYMENT_URL;

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("senderUserName", senderUserName);
        map.put("senderDeviceId", senderDeviceId);
        map.put("amount", amount);
        map.put("token", token);
        map.put("title", title);
        map.put("description", description);

        String httpBody = JSONUtils.getJsonString(map, HashMap.class);
        HttpURLConnection urlConnection = JustPayHTTPRequest.doPOSTRequest(urlString, httpBody);
        JSONObject jsonResponse = JustPayHTTPResponse.responseForRequest(urlConnection);
        if (jsonResponse != null) {
            // Response : true / false
        }
    }

    public void makePaymentForSender(String receiverUserName, int amount, String token, String title, String description) {

        String urlString = BASE_URL + PAYMENT_URL;

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("receiverUserName", receiverUserName);
        map.put("amount", amount);
        map.put("token", token);
        map.put("title", title);
        map.put("description", description);

        String httpBody = JSONUtils.getJsonString(map, HashMap.class);

        HttpURLConnection urlConnection = JustPayHTTPRequest.doPOSTRequest(urlString, httpBody);
        JSONObject jsonResponse = JustPayHTTPResponse.responseForRequest(urlConnection);
        if (jsonResponse != null) {
            // Response : true / false
        }
    }

    public void userInformation() {

        // TODO : GIVES LOGIN USER INFORMATION
        String urlString = BASE_URL + USER_INFO_URL;
        HttpURLConnection urlConnection = JustPayHTTPRequest.doGETRequest(urlString, "");
        JSONObject jsonResponse = JustPayHTTPResponse.responseForRequest(urlConnection);
        if (jsonResponse != null) {
            // Response : true / false
        }
    }

    public void userUpdate(HashMap map) {

        String urlString = BASE_URL + USER_UPDATE_URL;
        Gson gson = new Gson();
        String json = gson.toJson(map, HashMap.class);
        HttpURLConnection urlConnection = JustPayHTTPRequest.doPOSTRequest(urlString, json);
        JSONObject jsonResponse = JustPayHTTPResponse.responseForRequest(urlConnection);
        if (jsonResponse != null) {
            // Response : true / false
        }
    }

    @Override
    protected Void doInBackground(Object... params) {

        String paramTAG = (String) params[0];

        if (paramTAG.equals("REGISTRATION")) {
            JustPayUser justPayUser = (JustPayUser) params[1];
            userRegistration(justPayUser);
        } else if (paramTAG.equals("LOGIN")) {
            String userName = (String) params[1];
            String password = (String) params[2];
            userLogin(userName, password);
        } else if (paramTAG.equals("CHECK")) {
            String userName = (String) params[1];
            userCheck(userName);
        } else if (paramTAG.equals("INFORMATION")) {
            userInformation();
        } else if (paramTAG.equals("LOGOUT")) {
            userLogout();
        } else if (paramTAG.equals("PAYMENT_FOR_SENDER")) {
            String userName = (String) params[1];
            userCheck(userName);
        } else if (paramTAG.equals("PAYMENT_FOR_RECEIVER")) {
            String userName = (String) params[1];
            userCheck(userName);
        } else if (paramTAG.equals("UPDATE")) {
            String userName = (String) params[1];
            userCheck(userName);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

}
