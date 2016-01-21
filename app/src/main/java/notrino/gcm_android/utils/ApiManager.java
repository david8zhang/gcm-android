package notrino.gcm_android.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
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

    /** A nested Asynchronous Task class for getting GCM registration tokens. */
    private class ReqTask extends AsyncTask<String, String, String> {

        public String regToken;
        public String username;
        public String password;
        public String email;

        @Override
        protected String doInBackground(String... strings) {
            username = strings[0];
            password = strings[1];
            email = strings[2];
            InstanceID instanceID = InstanceID.getInstance(context);
            try {
                regToken = instanceID.getToken(Constants.SENDER_ID, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            System.out.println("Async: " + username);
        }
    }

    /** Create a new user. (Happens when onboarding). */
    public void createUser(ArrayList<String> parameters) {

        //Extract parameters
        String username = parameters.get(0);
        String password = parameters.get(1);
        String email = parameters.get(2);
        InstanceID instanceID = InstanceID.getInstance(context);

        System.out.println("username: " + username);
        System.out.println("password: " + password);
        System.out.println("email: " + email);
        ReqTask reqTask = (ReqTask) new ReqTask().execute(username, password, email);
    }
}
