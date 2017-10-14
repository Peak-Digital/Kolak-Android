package peak.org.kolok;

/**
 * Created by markrjr on 10/14/17.
 */

import android.content.Intent;
import android.util.Log;

import com.arasthel.asyncjob.AsyncJob;

import java.util.ArrayList;

import io.cloudboost.*;

public class KolokCloud {

    static String CLOUDBOOST_APP_ID = "mwkatnhcrwtl";
    static String CLOUDBOOST_API_KEY = "edc742e4-b5ed-4e17-ba74-c3b0ee855f72";

    static CloudApp cloudBoost = null;


    static void createUser(final String email, final String pass)
    {

        AsyncJob.doInBackground(new AsyncJob.OnBackgroundJob() {
            @Override
            public void doOnBackground() {

                CloudObject newUser = new CloudObject("User");

                 try
                    {
                        newUser.set("username", email);
                        newUser.set("email", email);
                        newUser.set("password", pass);

                        //Save the object
                        newUser.save(new CloudObjectCallback(){
                            @Override
                            public void done(CloudObject object, CloudException err) {
                                if(err != null){
                                    Log.d("Kolok", "Failed.");
                                    Log.d("Err", err.toString());
                                }
                                if(object != null){
                                    //object saved successfully
                                    AsyncJob.doOnMainThread(new AsyncJob.OnMainThreadJob() {
                                        @Override
                                        public void doInUIThread() {
                                            //doWhenFinished.methodToCallBack();
                                        }
                                    });
                                    Log.d("Kolok", "We did it.");
                                }
                            }
                        });
                    }
                    catch(CloudException e)
                    {
                        Log.d("Kolok", "Failed to contact cloudboost.");
                    }
            }
        });
    }

    static void loginUser(final String email, final String password, final CloudBoostCallback onSuccess) {
        Log.d("User: ", email);
        Log.d("Pass: ", password);
        AsyncJob.doInBackground(new AsyncJob.OnBackgroundJob() {
            @Override
            public void doOnBackground() {
                CloudUser user = new CloudUser();
                try
                {
                    user.setUserName(email);
                    user.setPassword(password);

                    //Login
                    user.logIn(new CloudUserCallback(){
                        @Override
                        public void done(CloudUser object, CloudException e) {
                            if(e != null){
                                Log.d("Kolok", "Failed to login.");
                                Log.d("Err", e.toString());
                            }
                            if(object != null){
                                //object saved successfully
                                Log.d("Kolok", "We logged in.");
                                onSuccess.methodToCallBack();
                            }
                        }
                    });
                }
                catch(CloudException e)
                {
                    Log.d("Kolok", "Failed to contact cloudboost.");
                }
            }
        });
    }

    static void getUser(final String email, final CloudBoostCallback onFailure, final CloudBoostCallback onSuccess)
    {
        Log.d("Kolok", "In getUser Function");
        AsyncJob.doInBackground(new AsyncJob.OnBackgroundJob() {
            @Override
            public void doOnBackground() {

                CloudQuery query = new CloudQuery("User");
                query.equalTo("email", email);

                try
                {
                    query.findOne(new CloudObjectCallback(){
                        @Override
                        public void done(CloudObject x, CloudException t) {
                            if(x != null){
                                Log.d("Kolok", x.getId());
                                onSuccess.methodToCallBack();
                            }
                            if(t != null){
                                //any errors
                                Log.d("Kolok", "Could not find User");
                                onFailure.methodToCallBack();
                            }
                        }
                    });
                }
                catch(CloudException e)
                {
                    Log.d("Kolok", "Failed to contact cloudboost.");
                }
            }
        });

    }

}
