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

/**
 * Activity that views a single posting and displays all its information.
 */
public class ViewSinglePosting extends AppCompatActivity {
    public static String POSTINGID = "POSTINGID";//gets postingID from main activity
    private String postingID = "";
    private String error = "";
    public String email = "";

    /**
     * Loads all the information from the posting ID and displays into the activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_posting); //sets view to activity
        postingID = getIntent().getStringExtra(POSTINGID); //gets the posting ID from main activity
        //Loads all the textViews and the ImageView from the xml.
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

        //loads information from posting ID passed from main activity and displays it.
        RequestParams rp = new RequestParams();
        HttpUtils.get("postings/"+ postingID, rp, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject artistData = response.getJSONObject("artist");
                    //Loads the information from the postingID
                    String artistIN = artistData.getString("name");
                    String titleIN = response.getString("title");
                    String descriptionIN = response.getString("description");
                    String priceIN = response.getString("price");
                    String artStatusIN = response.getString("artStatus");
                    String xDimIN = response.getString("xdim");
                    String yDimIN = response.getString("ydim");
                    String zDimIN = response.getString("zdim");
                    String imageURL = response.getString("image");

                    //displays the information of the posting
                    title.append(" " + titleIN);
                    artist.append(" " + artistIN);
                    description.append(" " + descriptionIN);
                    price.append(" $" + priceIN);
                    status.append(" " + artStatusIN);
                    xDim.append(" (inches) " + xDimIN);
                    yDim.append(" (inches) " + yDimIN);
                    zDim.append(" (inches) " + zDimIN);

                    //displays the image of the posting
                    Picasso.get().load(imageURL).resize(300, 300).centerInside().into(postingImage);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //Changes error message on failure
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

    /**
     * Changes the view from this activity to cart activity.
     * @param v: view of the user.
     * @throws UnsupportedEncodingException
     */
    public void addToCart(View v) throws UnsupportedEncodingException {

        error = "";
        String userEmail = "buyer@mail.com";
        RequestParams rp = new RequestParams();
        rp.add("email", userEmail);
        HttpUtils.post("/android/addToCart/" + postingID, rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                toCart();
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

    /**
     * Redirect the application to the Cart Activity
     * @param v: the view in which it is displayed.
     */
    public void toCart(){
        setContentView(R.layout.activity_cart);
        Intent intent= new Intent(this, Cart.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }

    /**
     * Updates error messages.
     */
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