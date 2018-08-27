package my.edu.utem.randomuser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView name, email, address, phone, dob;
    ImageView gambar;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.nameView);
        email = findViewById(R.id.emailView);
        address = findViewById(R.id.addressView);
        phone = findViewById(R.id.phoneView);
        dob = findViewById(R.id.dobView);
        gambar = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.pbloading);
    }

    public void getUser(View view) {
        progressBar.setVisibility(View.VISIBLE);
        // Instantiate the RequestQueue.
        //ni part volley
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://randomuser.me/api";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                                        new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {
                                                                try{
                                                                    JSONObject jsonObject = new JSONObject(response);
                                                                    JSONObject personObject = jsonObject.getJSONArray("results").getJSONObject(0);
                                                                    String firstName = personObject.getJSONObject("name").getString("first");
                                                                    String lastName = personObject.getJSONObject("name").getString("last");
                                                                    String title = personObject.getJSONObject("name").getString("title");
                                                                    String streetAdd = personObject.getJSONObject("location").getString("street");
                                                                    String cityAdd = personObject.getJSONObject("location").getString("city");
                                                                    String stateAdd = personObject.getJSONObject("location").getString("state");
                                                                    String poscode = personObject.getJSONObject("location").getString("postcode");
                                                                    String emailAdd = personObject.getString("email");
                                                                    String phoneNo = personObject.getString("phone");
                                                                    String dateOfBirth = personObject.getJSONObject("dob").getString("date");
                                                                    String picUrl = personObject.getJSONObject("picture").getString("large");

                                                                    name.setText(title + "  " + firstName + " " + lastName);
                                                                    email.setText(emailAdd);
                                                                    phone.setText(phoneNo);
                                                                    address.setText(streetAdd + " " + cityAdd + " " +  stateAdd + " " + poscode );
                                                                    dob.setText(dateOfBirth);
                                                                    Glide.with(MainActivity.this).load(picUrl).into(gambar);
                                                                    progressBar.setVisibility(View.GONE);
                                                                }
                                                                catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                    progressBar.setVisibility(View.GONE);
                                                                }
                                                                // Display the first 500 characters of the response string.
                                                                //change to response shj

                                                            }
                                                        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                name.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
