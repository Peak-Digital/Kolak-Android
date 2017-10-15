package peak.org.kolok;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;

/**
 * Created by markrjr on 10/14/17.
 */

public class SwipeAdapter extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;

    private ArrayList<HashMap<String, Object>> bait_topics;

    public SwipeAdapter(Context context, ArrayList<HashMap<String, Object>> bait_topics) {
        super(context, R.layout.bait_item, bait_topics);

        this.context = context;
        this.bait_topics = bait_topics;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.bait_item, parent, false);
        }

        final ImageView baitImage = (ImageView) convertView.findViewById(R.id.baitImage);

        Picasso
                .with(context)
                .load((String) bait_topics.get(position).get("image"))
                .into(baitImage);

        Target imageViewTarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                baitImage.setImageBitmap(bitmap);
                IssueActivity.issueActivity.showSwipeView();

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

                IssueActivity.issueActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        IssueActivity.issueActivity.showProgressBars();

                    }
                });

            }
        };

        return convertView;
    }
}
