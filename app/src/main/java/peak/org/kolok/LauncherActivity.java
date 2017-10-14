package peak.org.kolok;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
<<<<<<< HEAD

import com.github.paolorotolo.appintro.AppIntro;
=======
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.arasthel.asyncjob.AsyncJob;
import java.util.ArrayList;

import io.cloudboost.CloudException;
import io.cloudboost.CloudObject;
import io.cloudboost.CloudObjectCallback;
>>>>>>> origin/master


public class LauncherActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        // Note here that we DO NOT use setContentView();

        // Add your slide fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.

        if (KolokCloud.cloudBoost == null) {
            KolokCloud.cloudBoost.init(KolokCloud.CLOUDBOOST_APP_ID, KolokCloud.CLOUDBOOST_API_KEY);
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submitButton) {
            final EditText et = (EditText) findViewById(R.id.emailLogin);
            final EditText etPass = (EditText) findViewById(R.id.passLogin);
            final EditText etPassVer = (EditText) findViewById(R.id.passLoginVer);
            final String userEmail = et.getText().toString();
            KolokCloud.getUser(userEmail, new CloudBoostCallback() {
                @Override
                public void methodToCallBack() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            etPass.setVisibility(View.VISIBLE);
                            etPassVer.setVisibility(View.VISIBLE);
                            String userPass = etPass.getText().toString();
                            KolokCloud.createUser(userEmail, userPass);
                        }
                    });
                }
            }, new CloudBoostCallback() {
                @Override
                public void methodToCallBack() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            etPass.setVisibility(View.VISIBLE);
                            String userPass = etPass.getText().toString();
                            KolokCloud.loginUser(userEmail, userPass, new CloudBoostCallback() {
                                @Override
                                public void methodToCallBack() {
                                }
                            });
                        }
                    });
                }
            });
            Intent myIntent = new Intent(this, IssueActivity.class);
            startActivity(myIntent);
        }
    }
}