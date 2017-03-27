package www.circularblue.com.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    TextView mTextView;// = (TextView) findViewById(R.id.textView);
    Button button;
    Button sendDetails;
    EditText editText_ssid;
    EditText editText_password;
    String url ="http://192.168.4.1";
    // Instantiate the RequestQueue.
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.textView_logs);
        button = (Button) findViewById(R.id.button_connect);
        sendDetails = (Button) findViewById(R.id.button_send_details);
        editText_ssid = (EditText)findViewById(R.id.editText_ssid);
        editText_password = (EditText) findViewById(R.id.editText_password);
        queue = Volley.newRequestQueue(MainActivity.this);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connect();
            }
        });
        sendDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendWiFiDetails();
            }
        });
    }
    private void connect(){
        Log.d("debug","connect");

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        mTextView.setText("Response is: "+response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("cant connect");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    private void sendWiFiDetails(){

        String ssid = editText_ssid.getText().toString().trim();
        String password = editText_password.getText().toString().trim();

        ssid = ssid.replace(" ","%20");
        password = password.replace(" ","%20");

        Log.d("debug","ssid " + ssid);
        Log.d("debug","password " + password);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ssid",ssid);
            jsonObject.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = this.url + "/" + jsonObject.toString();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // your response
                        mTextView.setText("Response is: " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
                mTextView.setText("Error is: " +  error.getMessage());
            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("ssid","Circular Blue");
                    jsonObject.put("password","mightycartoon");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String your_string_json = jsonObject.toString(); // put your json
                return your_string_json.getBytes();
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
//        requestt_urlQueue.start();
    }


}
