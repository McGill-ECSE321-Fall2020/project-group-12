package ca.mcgill.ecse321.smartart;

import android.os.Bundle;
import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.JsonStreamerEntity;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
import cz.msebera.android.httpclient.HttpEntity;

public class MainActivity extends AppCompatActivity {
    private String error = null;
    private EditText localpostingID;
    private String title = "";
    String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        localpostingID = (EditText) findViewById(R.id.posting_id);          //goood stuff
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        refreshErrorMessage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void viewPostings(View v) {
        error = "";
        final LinearLayout postings = (LinearLayout) findViewById(R.id.postings);

        // final TextView tv = (TextView) findViewById(R.id.error);
        final ListView displayPostings = (ListView) findViewById(R.id.textViewPostings);
        final ImageView displayImages = (ImageView) findViewById(R.id.textViews);
        // Initializing a new String Array
        String[] artworks = new String[] {

        };
        // Create a List from String Array elements
        final List<String> artwork_list = new ArrayList<String>(Arrays.asList(artworks));
        // Create an ArrayAdapter from List
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, artwork_list);

        // DataBind ListView with items from ArrayAdapter
        displayPostings.setAdapter(arrayAdapter);

        HttpUtils.get("postings", new RequestParams(), new JsonHttpResponseHandler() {
            private JSONArray response;
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                postings.removeAllViews();
                //displayPostings.setText("");
                for (int i = 0; i < response.length(); i++) {

                    try {

                        JSONObject jsonobject = response.getJSONObject(i);
                        String title = jsonobject.getString("title");
                        String urlImage = jsonobject.getString("image");
                        String description = jsonobject.getString("description");
                        String art = title + " " + description;
                        artwork_list.add(art);
                        arrayAdapter.notifyDataSetChanged();
                        Picasso.get().load(urlImage).into(displayImages);

                        TextView textView = new TextView(MainActivity.this);
                        textView.setText(title + " " + description);;
                        postings.addView(textView);

                        Button myButton = new Button(MainActivity.this);
                        postings.addView(myButton);
                        myButton.setText("View Posting");
                        int postingID = jsonobject.getInt("postingID");
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
// ...

    }

    public void getPostingName(View v) {
        error = "";
        String postingID = localpostingID.getText().toString();
        //   final EditText tv = (EditText) findViewById(R.id.posting_id);
        final TextView postingName = (TextView) findViewById(R.id.postID);
        // String number = "1126284095"; 1619110225
        RequestParams rp = new RequestParams();
        //    rp.add("postingID", postingID);
        HttpUtils.get("postings/"+ postingID,rp, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                postingName.setText("");
                try {
                    System.out.println(title);
                    title= response.getString("title");
                    postingName.append(" name is:   "+title);
                    //       System.out.println(title);
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
