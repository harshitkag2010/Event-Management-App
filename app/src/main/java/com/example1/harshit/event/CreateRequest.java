package com.example1.harshit.event;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Harshit on 10/3/2016.
 */
public class CreateRequest extends StringRequest {


    private static final String CREATE_REQUEST_URL="http://manral10.net16.net/createvent.php";

    private Map<String, String> params;

    public CreateRequest(String title, String venue, String details,int hours,int minutes,String SETtime,String SETdate, Response.Listener<String> listener){
        super(Method.POST, CREATE_REQUEST_URL, listener, null);

        params =new HashMap<>();
        params.put("title", title);
        params.put("venue",venue);
        params.put("details",details);
        params.put("hours", hours+"");
        params.put("minutes", minutes+"");
      //  params.put("time", time);
        params.put("SETtime", SETtime);
        params.put("SETdate",SETdate );
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
