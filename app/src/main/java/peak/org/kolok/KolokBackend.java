package peak.org.kolok;

import android.util.Log;
import android.widget.ListAdapter;

import com.arasthel.asyncjob.AsyncJob;
import com.bluelinelabs.logansquare.LoganSquare;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by markrjr on 10/14/17.
 */

public class KolokBackend {

    private static final String backendServer = "http://kolok.reachthepeak.co";

    public static void getBait(final KolokCallback onFailure, final KolokDataCallback onSuccess)
    {
        AsyncJob.doInBackground(new AsyncJob.OnBackgroundJob() {
            @Override
            public void doOnBackground() {

                OkHttpClient httpClient = new OkHttpClient();

                try
                {
                    Request request = new Request.Builder()
                            .url(backendServer + "/api/unified")
                            .build();

                    Response response = httpClient.newCall(request).execute();


                    List<HashMap<String, String>> topics = LoganSquare.parse(response.body().string(), List.class);

                    onSuccess.methodToCall(topics);

                }
                catch (IOException e)
                {
                    Log.e("Kolok", "Failed to get bait topics.");
                    onFailure.methodToCall();
                }

            }
        });


    }

}
