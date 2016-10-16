package com.example1.harshit.event;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewEvent extends AppCompatActivity implements View.OnClickListener {


    private EditText editTextId;
    private Button buttonGet;
    private TextView textViewResult;

    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

        editTextId = (EditText) findViewById(R.id.editTextId);
        buttonGet = (Button) findViewById(R.id.buttonGet);
        textViewResult = (TextView) findViewById(R.id.textViewResult);

        buttonGet.setOnClickListener(this);
    }

    private void getData() {
        String id = editTextId.getText().toString().trim();
       // Log.e("TAG",id);
       // Log.e("TAG","246");
        if (id.equals("")) {
            Toast.makeText(this, "Please enter an id", Toast.LENGTH_LONG).show();
            return;
        }
        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url = Config.DATA_URL+editTextId.getText().toString().trim();
      //  Log.e("TAG",url);
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();

               // Log.e("TAG","1111");
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ViewEvent.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){

      //  Log.e("TAG","24346");
        String title="";
        String venue="";
        String details = "";
        String Time="";
        String Date="";
        int index=0;
        for(int i=0;i<response.length();i++)
        {
            if(response.charAt(i)=='<')
            {
                index = i;
                break;
            }
        }
        response = response.substring(16,index);
        response = response.trim();
        try {
         //  Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject cg = result.getJSONObject(0);
            title = cg.getString(Config.KEY_title);
            venue = cg.getString(Config.KEY_venue);
            details = cg.getString(Config.KEY_details);
            Time=cg.getString(Config.KEY_time);
            Date= cg.getString(Config.KEY_date);
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(),"Raised",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        textViewResult.setText("Name:\t"+title+"\nLocation:\t" +venue+ "\nEvent Details:\t"+ details+"\nTime:\t"+Time+"\nDate:\t"+Date);
    }

    @Override
    public void onClick(View v) {

        getData();
    }

}