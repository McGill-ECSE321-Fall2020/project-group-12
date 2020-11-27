package ca.mcgill.ecse321.smartart;

//import android.content.Context;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.Spinner;
//import android.widget.TextView;
//import androidx.appcompat.widget.Toolbar;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.snackbar.Snackbar;
//import com.loopj.android.http.JsonHttpResponseHandler;
//import com.loopj.android.http.RequestParams;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import cz.msebera.android.httpclient.Header;
//import cz.msebera.android.httpclient.HttpEntity;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class Cart extends AppCompatActivity{
//    private String error = null;
//    private JSONObject cart;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_cart);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        Spinner spinner = (Spinner) findViewById(R.id.deliveryTypeSpinner);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.deliveryType, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        refreshErrorMessage();
//    }
//
//    public void getCart(View v) {
//        error = "";
//        String userEmail = null; //needs edit
//        final TextView displayCartPostings = (TextView) findViewById(R.id.textViewCartPostings); //needs edit
//        final TextView displayTotalPrice = (TextView) findViewById(R.id.textViewTotalPrice); //needs edit
//        RequestParams rp = new RequestParams();
//        HttpUtils.get("/purchases/cart/"+ userEmail,rp, new JsonHttpResponseHandler(){
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                cart = response;
//                displayCartPostings.setText("");
//                try {
//                    int totalPrice = response.getInt("totalPrice");
//                    displayTotalPrice.append((String.valueOf(totalPrice)));
//                    JSONArray cartPostings = response.getJSONArray("postings");
//                    for (int i = 0; i < cartPostings.length() ; i++){
//                        JSONObject posting = cartPostings.getJSONObject(i);
//                        String title = posting.getString("title");
//                        String description = posting.getString("description");
//                        displayCartPostings.append(title + "   ");
//                        displayCartPostings.append(" Description:   " + description + "\n");
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                try {
//                    error += errorResponse.get("message").toString();
//                } catch (JSONException e) {
//                    error += e.getMessage();
//                }
//                refreshErrorMessage();
//            }
//        });
//    }
//
//    public void makePurchase(View v) {
//        error = "";
//        String userEmail = null; //needs edit
//        final TextView deliveryType = (TextView) findViewById(R.id.textViewCartPostings);
//        Context context = null;
//        HttpEntity entity = null;
//        String contentType;
//        HttpUtils.post(context, "/purchases/make/"+ deliveryType, entity, contentType, new JsonHttpResponseHandler(){
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                try {
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                try {
//                    error += errorResponse.get("message").toString();
//                } catch (JSONException e) {
//                    error += e.getMessage();
//                }
//                refreshErrorMessage();
//            }
//        });
//    }
//
//
//
//    private void refreshErrorMessage() {
//        // set the error message
//        TextView tvError = (TextView) findViewById(R.id.error);
//        tvError.setText(error);
//
//        if (error == null || error.length() == 0) {
//            tvError.setVisibility(View.GONE);
//        } else {
//            tvError.setVisibility(View.VISIBLE);
//        }
//    }
//}
