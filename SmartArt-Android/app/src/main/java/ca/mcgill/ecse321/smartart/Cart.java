package ca.mcgill.ecse321.smartart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;

import androidx.appcompat.app.AppCompatActivity;

public class Cart extends AppCompatActivity {
    public static String email = "mail";
    private String error = null;
    private String userEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        userEmail = getIntent().getStringExtra(email);
        getCart();

        refreshErrorMessage();
    }

    public void getCart() {
        error = "";

        System.out.println("at1");
        System.out.println(userEmail + "at 2");
        final TextView displayCartPostings = (TextView) findViewById(R.id.textViewCartPostings);
        final TextView displayTotalPrice = (TextView) findViewById(R.id.textViewTotalPrice);
        RequestParams rp = new RequestParams();
        HttpUtils.get("/purchases/cart/" + userEmail, rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                displayCartPostings.setText("");
                try {
                    int totalPrice = response.getInt("totalPrice");
                    displayTotalPrice.append((String.valueOf(totalPrice)));
                    JSONArray cartPostings = response.getJSONArray("postings");
                    for (int i = 0; i < cartPostings.length(); i++) {
                        JSONObject posting = cartPostings.getJSONObject(i);
                        String title = posting.getString("title");
                        String description = posting.getString("description");
                        displayCartPostings.append(title + "   ");
                        displayCartPostings.append(" Description:   " + description + "\n");
                    }

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

    public void toHome(View v){
        setContentView(R.layout.activity_main);
        Intent intent= new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.email, email);
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
