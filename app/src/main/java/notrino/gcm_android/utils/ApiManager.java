package notrino.gcm_android.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import notrino.gcm_android.controllers.AppController;
import notrino.gcm_android.models.Constants;
import notrino.gcm_android.models.DataRequest;

/**
 * Created by david_000 on 1/19/2016.
 */
public class ApiManager {

    /** The current application context. (Used for AWS) */
    private Context context;

    public ApiManager(Context context) {
        this.context = context;
    }

    /** Create a new user. (Happens when onboarding). */
    public void createUser(HashMap<String, String> params) {
        InstanceID instanceID = InstanceID.getInstance(context);
        try {
            String reg_token = instanceID.getToken(Constants.SENDER_ID, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            params.put("reg_token", reg_token);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DataRequest request = new DataRequest(Request.Method.POST, null, params, Constants.CREATE_USER_API, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                System.out.println(jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println(volleyError);
            }
        });
        if(request == null) {
            System.out.println("Something went wrong");
        }
        if(AppController.getInstance() == null) {
            System.out.println("Something went wrong with app controller");
        }
//        AppController.getInstance().addToRequestQueue(request);
    }
}
