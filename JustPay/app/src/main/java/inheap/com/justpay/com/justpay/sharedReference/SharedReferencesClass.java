package inheap.com.justpay.com.justpay.sharedReference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ABHISHEK on 11/12/2016.
 */

public class SharedReferencesClass {

    private static final String PREF_NAME = "JP_SHARED_PREFS";
    private static final String LOGIN_USER_NAME = "LOGIN_USER_NAME";
    private static final String LOGIN_USER_PASSWORD = "LOGIN_USER_PASSWORD";
    private static final String LOGIN_USER_FIRST_NAME = "LOGIN_USER_NAME";
    private static final String LOGIN_USER_LAST_NAME = "LOGIN_USER_LAST_NAME";
    private static final String LOGIN_USER_EMAIL = "LOGIN_USER_EMAIL";
    private static final String LOGIN_USER_PHONENO = "LOGIN_USER_PHONENO";


    public SharedPreferences sharedRef;
    public static SharedReferencesClass userPref;
    private Context context;

    public SharedReferencesClass(Context context) {                     // class constructor
        this.context = context;
        sharedRef = this.context.getSharedPreferences(PREF_NAME, 0);
    }

    public static SharedReferencesClass getInstance(Context context) {   // singleton pattern
        if(userPref == null) {
            userPref = new SharedReferencesClass(context.getApplicationContext());
        }
        return userPref;
    }

    public void setUserName(String userName) {
        SharedPreferences.Editor editor = sharedRef.edit();
        editor.putString(LOGIN_USER_NAME, userName);
    }

    public String getUserName() {
        return sharedRef.getString(LOGIN_USER_NAME,"");
    }

    public void setPassword(String password) {
        SharedPreferences.Editor editor = sharedRef.edit();
        editor.putString(LOGIN_USER_PASSWORD, password);
    }

    public String getPassword() {
        return sharedRef.getString(LOGIN_USER_PASSWORD,"");
    }

    public void setFirstName(String firstName) {
        SharedPreferences.Editor editor = sharedRef.edit();
        editor.putString(LOGIN_USER_FIRST_NAME, firstName);
    }

    public String getFirstName() {
        return sharedRef.getString(LOGIN_USER_LAST_NAME,"");
    }

    public void setLastName(String lastName) {
        SharedPreferences.Editor editor = sharedRef.edit();
        editor.putString(LOGIN_USER_FIRST_NAME, lastName);
    }

    public String getLastName() {
        return sharedRef.getString(LOGIN_USER_LAST_NAME,"");
    }

    public void setEmail(String email) {
        SharedPreferences.Editor editor = sharedRef.edit();
        editor.putString(LOGIN_USER_EMAIL, email);
    }

    public String getEmail() {
        return sharedRef.getString(LOGIN_USER_EMAIL,"");
    }

    public void setPhoneNo(String phoneNo) {
        SharedPreferences.Editor editor = sharedRef.edit();
        editor.putString(LOGIN_USER_PHONENO, phoneNo);
    }

    public String getPhoneNo() {
        return sharedRef.getString(LOGIN_USER_PHONENO,"");
    }

}
