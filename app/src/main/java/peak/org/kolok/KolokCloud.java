package peak.org.kolok;

/**
 * Created by markrjr on 10/14/17.
 */

import android.util.Log;

import com.arasthel.asyncjob.AsyncJob;

import java.util.ArrayList;

import io.cloudboost.*;

public class KolokCloud {

    static String CLOUDBOOST_APP_ID = "mwkatnhcrwtl";
    static String CLOUDBOOST_API_KEY = "edc742e4-b5ed-4e17-ba74-c3b0ee855f72";

    static CloudApp cloudBoost = null;


    static void createUser()
    {
        AsyncJob.doInBackground(new AsyncJob.OnBackgroundJob() {
            @Override
            public void doOnBackground() {

                CloudObject newUser = new CloudObject("User");

                 try
                    {
                        newUser.set("blah", "blah");

                        //Save the object
                        newUser.save(new CloudObjectCallback(){
                            @Override
                            public void done(CloudObject object, CloudException err) {
                                if(err != null){
                                    Log.d("Kolok", "Failed.");
                                }
                                if(object != null){
                                    //object saved successfully
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

    static void getUser(final String email)
    {

        AsyncJob.doInBackground(new AsyncJob.OnBackgroundJob() {
            @Override
            public void doOnBackground() {

                CloudQuery query = new CloudQuery("User");
                query.equalTo("email", email);

                try
                {
                    query.find(new CloudObjectArrayCallback(){
                        @Override
                        public void done(CloudObject[] x, CloudException t) {
                            if(x != null){
                                //objects
                            }
                            if(t != null){
                                //any errors
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
