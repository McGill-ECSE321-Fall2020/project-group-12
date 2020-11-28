package ca.mcgill.ecse321.smartart;

import android.os.Bundle;
import android.content.Intent;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;



import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    public static String email = "mail";
    private String error = null;
    private EditText localpostingID;
    private String title = "";
    private String uEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        localpostingID = (EditText) findViewById(R.id.posting_id);
        uEmail = getIntent().getStringExtra(email);
        refreshErrorMessage();
    }

    //Method to fetch and display all postings from the backend
    public void viewPostings(View v) {
        error = "";

        //Set the layout for the postings to be displayed
        final LinearLayout postings = (LinearLayout) findViewById(R.id.postings);
        //Backend call to return all postings in the database
        HttpUtils.get("postings", new RequestParams(), new JsonHttpResponseHandler() {
            private JSONArray response;
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                postings.removeAllViews();
                //Iterate through each posting to add it to the display
                for (int i = 0; i < response.length(); i++) {

                    try {
                        //Declare data object so that its contents can be extracted
                        JSONObject jsonobject = response.getJSONObject(i);
                        //Extract all necessary attributes as strings
                        String title = jsonobject.getString("title");
                        String urlImage = jsonobject.getString("image");
                        String description = jsonobject.getString("description");
                        String art = title + ": " + description;
                        //add the art as a string to the display
                        int postingID = jsonobject.getInt("postingID");
                        Button myButton = new Button(MainActivity.this);
                        postings.addView(myButton);
                        myButton.setText(title);
                        myButton.setY(100);
                        myButton.setX(-80);
                        myButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this, ViewSinglePosting.class);
                                intent.putExtra(ViewSinglePosting.POSTINGID, postingID + "");
                                startActivity(intent);
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }


        });

    }

    //Method to return the title of a posting from its posting ID
    public void getPostingName(View v) {
        error = "";
        //Bind the text box input to local string to be used in backend call
        String postingID = localpostingID.getText().toString();
        final TextView postingName = (TextView) findViewById(R.id.postID);
        //Backend call to return a posting from its posting ID
        HttpUtils.get("postings/"+ postingID,new RequestParams(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //Display the title of the desired posting
                postingName.setText("");
                try {
                    //Extract and store the title of the desired posting to be returned to the display
                    title= response.getString("title");
                    //Update display to show name
                    postingName.append(" name is:   "+title);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }

    //Method to redirect the app to the Login Activity
    public void toLogin(View v){
        setContentView(R.layout.login);
        Intent intent= new Intent(this, Login.class);
        startActivity(intent);
    }

    public void toCart(View v){
        setContentView(R.layout.activity_cart);
        Intent intent= new Intent(this, Cart.class);
        System.out.println(uEmail+"at3");
        intent.putExtra(Cart.email, uEmail);
        startActivity(intent);
    }

    private void refreshErrorMessage() {
        // set the error message
        TextView tvError = (TextView) findViewById(R.id.error);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }
}
