package inheap.com.justpay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import inheap.com.justpay.com.justpay.serverManager.JustPayServerManager;
import inheap.com.justpay.com.justpay.sharedReference.SharedReferencesClass;
import inheap.com.justpay.com.justpay.user.JustPayUser;

public class MainActivity extends AppCompatActivity {


    EditText userNameField;
    EditText passwordField;
    Button loginButton;
    Button signUpButton;
    public JustPayServerManager.CompletionListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameField = (EditText) findViewById(R.id.editText);
        passwordField = (EditText) findViewById(R.id.editText2);
        loginButton = (Button) findViewById(R.id.button);
        signUpButton = (Button) findViewById(R.id.button2);

        listener = new JustPayServerManager.CompletionListener() {

            @Override
            public void loginListener(String tag, Object[] array) {
                // TODO : CALLBACKS
            }

            @Override
            public void registrationListener(String tag, Object[] array) {

            }

            @Override
            public void paymentListener(String tag, Object[] array) {

            }

            @Override
            public void userUpdateListener(String tag, Object[] array) {

            }

            @Override
            public void userInfoListener(String tag, Object[] array) {

            }

            @Override
            public void userCheckListener(String json, Boolean flag) {

            }
        };

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateCredentials()) {
                    return;
                }

                // TODO : SAVE USER PREFRENCES
                SharedReferencesClass sharedReferences = SharedReferencesClass.getInstance(getApplicationContext());
                sharedReferences.setUserName(userNameField.getText().toString());
                sharedReferences.setPassword(passwordField.getText().toString());

                // TODO : CREATE USER OBJECT

                JustPayUser justPayUser = new JustPayUser();
                justPayUser.userName = userNameField.getText().toString();
                justPayUser.password = passwordField.getText().toString();

                // TODO : LOGIN SERVER CALL
                JustPayServerManager serverManager = new JustPayServerManager(listener);

                Object[] inputArray = new Object[2];
                inputArray[0] = "REGISTRATION";
                inputArray[1] = justPayUser;

                serverManager.execute(inputArray);

                // TODO : DO NOT CALL SERVER IN MAIN THREAD
                // TODO : IMPLEMENT CALL BACK VIA INTERFACES
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateCredentials()) {
                    return;
                }

                // TODO : SAVE USER PREFRENCES
                SharedReferencesClass sharedReferences = SharedReferencesClass.getInstance(getApplicationContext());
                sharedReferences.setUserName(userNameField.getText().toString());
                sharedReferences.setPassword(passwordField.getText().toString());

                // TODO : CREATE USER OBJECT
                JustPayUser justPayUser = new JustPayUser();
                justPayUser.userName = userNameField.getText().toString();
                justPayUser.password = passwordField.getText().toString();

                // TODO : LOGIN SERVER CALL
                JustPayServerManager serverManager = new JustPayServerManager(listener);

                Object[] inputArray = new Object[4];
                inputArray[0] = "LOGIN";
                inputArray[1] = userNameField.getText().toString();
                inputArray[2] = passwordField.getText().toString();
                serverManager.execute(inputArray);

                // TODO : DO NOT CALL SERVER IN MAIN THREAD
                // TODO : IMPLEMENT CALL BACK VIA INTERFACES
            }
        });
    }

    public boolean validateCredentials() {
        if (userNameField.getText().toString().isEmpty() || passwordField.getText().toString().isEmpty()) {
            Toast toast = Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        return true;
    }
}
