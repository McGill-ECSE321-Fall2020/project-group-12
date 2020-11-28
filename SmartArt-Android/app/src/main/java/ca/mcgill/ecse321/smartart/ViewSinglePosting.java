package ca.mcgill.ecse321.smartart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class ViewSinglePosting extends AppCompatActivity {
    public static String POSTINGID = "POSTINGID";
    private String postingID = "";
    private String error = "";
    public String userEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_posting);
        postingID = getIntent().getStringExtra(POSTINGID);
        TextView title = findViewById(R.id.title2);
        TextView artist = findViewById(R.id.artist);
        TextView status = findViewById(R.id.artStatus);
        TextView price = findViewById(R.id.price);
        TextView xDim = findViewById(R.id.xDim);
        TextView yDim = findViewById(R.id.yDim);
        TextView zDim = findViewById(R.id.zDim);
        TextView description = findViewById(R.id.description);
        ImageView postingImage = (ImageView)findViewById(R.id.postingImage);
        error = "";

        RequestParams rp = new RequestParams();
        HttpUtils.get("postings/"+ postingID, rp, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject artistData = response.getJSONObject("artist");
                    String artistIN = artistData.getString("name");
                    String titleIN = response.getString("title");
                    String descriptionIN = response.getString("description");
                    String priceIN = response.getString("price");
                    String artStatusIN = response.getString("artStatus");
                    String xDimIN = response.getString("xdim");
                    String yDimIN = response.getString("ydim");
                    String zDimIN = response.getString("zdim");
                    String imageURL = response.getString("image");

                    title.append(" " + titleIN);
                    artist.append(" " + artistIN);
                    description.append(" " + descriptionIN);
                    price.append(" $" + priceIN);
                    status.append(" " + artStatusIN);
                    xDim.append(" (inches) " + xDimIN);
                    yDim.append(" (inches) " + yDimIN);
                    zDim.append(" (inches) " + zDimIN);

                    Picasso.get().load(imageURL).resize(300, 300).centerInside().into(postingImage);


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

    public void addToCart(View v) throws UnsupportedEncodingException {
        error = "";
        RequestParams rp = new RequestParams();
        rp.add("email", userEmail); //takes the provided email and adds it to be sent
        HttpUtils.post("/android/addToCart/" + postingID, rp, new JsonHttpResponseHandler() { //posts the current posting to the cart based on the provided email
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                toCart(); //redirects to cart on success
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

    //navigates to the cart when the button is pressed
    public void toCart(){
        setContentView(R.layout.activity_cart);
        Intent intent= new Intent(this, Cart.class);
        intent.putExtra(Cart.email, userEmail);
        startActivity(intent);
    }
    private void refreshErrorMessage() {
        // set the error message
        TextView tvError = (TextView) findViewById(R.id.error2);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }
}