package peak.org.kolok;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daprlabs.cardstack.SwipeDeck;

import java.util.ArrayList;

public class IssueActivity extends AppCompatActivity {

    private ProgressBar loadingBar;
    private TextView loadingText;
    private SwipeDeck cardStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);

        if(KolokCloud.cloudBoost == null)
        {
            KolokCloud.cloudBoost.init(KolokCloud.CLOUDBOOST_APP_ID, KolokCloud.CLOUDBOOST_API_KEY);
        }


        loadingBar = (ProgressBar) findViewById(R.id.loadingBar);
        loadingText = (TextView) findViewById(R.id.loadingText);

        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);

        final ArrayList<String> testData = new ArrayList<>();
        testData.add("0");
        testData.add("1");
        testData.add("2");
        testData.add("3");
        testData.add("4");

        final SwipeDeckAdapter adapter = new SwipeDeckAdapter(testData, this);
        cardStack.setAdapter(adapter);

        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {

            @Override
            public void cardActionDown()
            {

            }

            @Override
            public void cardActionUp()
            {

            }

            @Override
            public void cardSwipedLeft(int position) {
                Log.i("MainActivity", "card was swiped left, position in adapter: " + position);
            }

            @Override
            public void cardSwipedRight(int position) {
                Log.i("MainActivity", "card was swiped right, position in adapter: " + position);
            }

            @Override
            public void cardsDepleted() {
                Log.i("MainActivity", "no more cards");
            }
        });


    }
}
