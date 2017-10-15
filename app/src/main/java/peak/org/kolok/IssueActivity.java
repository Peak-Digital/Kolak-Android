package peak.org.kolok;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static android.view.View.GONE;

public class IssueActivity extends AppCompatActivity {

    private ProgressBar loadingBar;
    private TextView loadingText;
    private SwipeFlingAdapterView baitSwipeView;
    private RelativeLayout optionsContainer;
    private TextView baitQuestion;
    private TextView baitSummary;
    private ArrayList<String> loadingPhrases;

    private ArrayList<HashMap<String, String>> baitTopics;
    private SwipeAdapter swipeAdapter;
    private SwipeFlingAdapterView baitSwipeFrame;

    public static IssueActivity issueActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_card);

        if(KolokCloud.cloudBoost == null)
        {
            KolokCloud.cloudBoost.init(KolokCloud.CLOUDBOOST_APP_ID, KolokCloud.CLOUDBOOST_API_KEY);
        }

        issueActivity = this;

        loadingBar = (ProgressBar) findViewById(R.id.loadingBar);
        loadingText = (TextView) findViewById(R.id.loadingText);

        loadingPhrases = new ArrayList<String>();

        loadingPhrases.add(getString(R.string.loading_almonds));
        loadingPhrases.add(getString(R.string.loading_beans));
        loadingPhrases.add(getString(R.string.loading_original));
        loadingPhrases.add(getString(R.string.loading_seabass));

        loadingText.setText(loadingPhrases.get(new Random().nextInt(loadingPhrases.size())));

        baitSwipeView = (SwipeFlingAdapterView) findViewById(R.id.baitSwipeFrame);
        optionsContainer = (RelativeLayout) findViewById(R.id.optionsContainer);
        baitQuestion = (TextView) findViewById(R.id.baitQuestion);
        baitSummary = (TextView) findViewById((R.id.baitSummary));

        KolokBackend.getBait(new KolokCallback() {
            @Override
            public void methodToCall() {
                Log.e("Kolak", "You done fucked up.");
            }
        }, new KolokDataCallback() {
            @Override
            public void methodToCall(final Object data) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        baitTopics = (ArrayList<HashMap<String, String>>) data;

                        setupSwipeView();

                        showSwipeView();
                    }
                });

            }
        });

    }

    public void showSwipeView()
    {
        if(baitSwipeView.getVisibility() == GONE)
        {
            loadingBar.setVisibility(GONE);
            loadingText.setVisibility(GONE);


            baitSwipeView.setVisibility(View.VISIBLE);
            baitQuestion.setVisibility(View.VISIBLE);
            optionsContainer.setVisibility(View.VISIBLE);
            baitQuestion.setVisibility(View.VISIBLE);
        }
    }

    public void showProgressBars()
    {
        if(loadingBar.getVisibility() == GONE)
        {
            baitSwipeView.setVisibility(View.GONE);
            baitQuestion.setVisibility(View.GONE);
            optionsContainer.setVisibility(View.GONE);
            baitQuestion.setVisibility(View.GONE);

            loadingBar.setVisibility(View.VISIBLE);
            loadingText.setVisibility(View.VISIBLE);
        }
    }

    private void setTopicText(HashMap<String, String> topic)
    {
        String summaryLine = "Source | " + "<b>" + topic.get("source") + "</b>" + "<br><br>" + topic.get("summary");

        baitQuestion.setText(topic.get("title"));
        baitSummary.setText(Html.fromHtml(summaryLine));
    }

    private void setupSwipeView()
    {


        //add the view via xml or programmatically
        baitSwipeFrame = (SwipeFlingAdapterView) findViewById(R.id.baitSwipeFrame);


        //choose your favorite adapter
        swipeAdapter = new SwipeAdapter(getApplicationContext(), baitTopics);

        //set the listener and the adapter
        baitSwipeFrame.setAdapter(swipeAdapter);

        baitSwipeFrame.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                baitTopics.remove(0);
                swipeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject

                if(baitTopics.size() > 0)
                {
                    Log.d("KOLOK: ", "Setting Image");
                    setTopicText(baitTopics.get(baitSwipeFrame.getFirstVisiblePosition()));
                }

            }

            @Override
            public void onRightCardExit(Object dataObject) {
//                Toast.makeText(getApplicationContext(), "Right!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
//                baitTopics.add("XML ".concat(String.valueOf(element)));
                swipeAdapter.notifyDataSetChanged();
                Log.d("LIST", "notified");
            }

            @Override
            public void onScroll(float v) {

            }


        });

        // Optionally add an OnItemClickListener
        baitSwipeFrame.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(baitTopics.get(itemPosition).get("url")));
                startActivity(browserIntent);

            }
        });

        setTopicText(baitTopics.get(baitSwipeFrame.getFirstVisiblePosition()));
    }
}
