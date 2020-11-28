package ca.mcgill.ecse321.smartart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

//Activity class for Login Activity
public class Login extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private String user = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initialize activity layout
        setContentView(R.layout.login);
        //Bind text box inputs to local variables to be used in login process
        email = findViewById(R.id.email_box);
        password = findViewById(R.id.password_box);
    }

    //Method to login using a call to the backend
    public void login(View v) {
        RequestParams params = new RequestParams();
        //Add login parameters
        params.add("email", email.getText().toString());
        params.add("password", password.getText().toString());
        //Backend call to authenticate the login credentials
        HttpUtils.get("android/login", params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //clear fields for next login
                email.setText("");
                password.setText("");
                try {
                    //store user email locally to be passed to other activities
                    user = response.getString("email");
                    //Redirect to main method
                    toMain();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                System.out.println(errorResponse);
                final TextView displayError = (TextView) findViewById(R.id.login_error);
                //clear text boxes for next login attempt
                email.setText("");
                password.setText("");
                displayError.append("Login Unsuccessful");
            }
        });


    }

    //Method to redirect app to the main activity
    public void toMain(){
        setContentView(R.layout.activity_main);
        Intent intent= new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.email, user);
        startActivity(intent);
    }

}