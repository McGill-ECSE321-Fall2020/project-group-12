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

public class Login extends AppCompatActivity {

    private String error = "";
    private EditText email;
    private EditText password;
    private String success = "";
    private String user = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        email = findViewById(R.id.email_box);
        password = findViewById(R.id.password_box);
    }

    public void login(View v) {
        RequestParams params = new RequestParams();
        params.add("email", email.getText().toString());
        params.add("password", password.getText().toString());
        HttpUtils.get("android/login", params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                email.setText("");
                password.setText("");
                try {
                    user = response.getString("email");
                    sendUser();
                    toMain();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                System.out.println(errorResponse);
                final TextView displayError = (TextView) findViewById(R.id.login_error);
                email.setText("");
                password.setText("");
                displayError.append("Login Unsuccessful");
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
            }
        });


    }

    public void toMain(){
        setContentView(R.layout.activity_main);
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void sendUser(){
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }
    //to get in your class:
    //String userEmail = getIntent().getStringExtra("USER");


}