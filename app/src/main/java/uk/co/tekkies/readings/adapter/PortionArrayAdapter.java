/*
Copyright 2013 Andrew Joiner

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package org.navigatebyfaith.rrreadings.adapter;

import java.util.ArrayList;
import java.util.List;

import org.navigatebyfaith.rrreadings.R;
import org.navigatebyfaith.rrreadings.ReadingsApplication;
import org.navigatebyfaith.rrreadings.activity.PassageActivity;
import org.navigatebyfaith.rrreadings.activity.ReadingsActivity;
import org.navigatebyfaith.rrreadings.day.DayFragment;
import org.navigatebyfaith.rrreadings.model.ParcelableReadings;
import org.navigatebyfaith.rrreadings.model.Passage;
import org.navigatebyfaith.rrreadings.model.Prefs;
import org.navigatebyfaith.rrreadings.util.Analytics;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import androidx.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class PortionArrayAdapter extends ArrayAdapter<Passage> implements OnClickListener {

    private ReadingsActivity readingsActivity;
    private ArrayList<Passage> passages;
    Prefs prefs;


    public PortionArrayAdapter(Activity activity, ArrayList<Passage> values) {
        super(activity, R.layout.listitem_portion, values);
        this.readingsActivity = (ReadingsActivity) activity;
        this.passages = values;
        prefs = new Prefs(activity);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) readingsActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.listitem_portion, parent, false);
        TextView textViewPassageTitle = (TextView) view.findViewById(R.id.passage_title);
        TextView textViewSummary = (TextView) view.findViewById(R.id.textViewSummary);
        textViewPassageTitle.setText(passages.get(position).getTitle());
        view.setTag(passages.get(position).getTitle());
        textViewPassageTitle.setOnClickListener(this);
        view.findViewById(R.id.imageViewReadOffline).setOnClickListener(this);
        view.findViewById(R.id.imageViewReadOnline).setOnClickListener(this);
        textViewSummary.setOnClickListener(this);

        if (ReadingsApplication.getMp3Installed()) {
            View listenView = view.findViewById(R.id.imageListen);
            listenView.setVisibility(View.VISIBLE);
            listenView.setOnClickListener(this);
        }
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(readingsActivity);
        Boolean showSummary = settings.getBoolean(Prefs.PREF_SHOW_SUMMARY, true);
        if (showSummary) {
            textViewSummary.setText(passages.get(position).getSummary());
        } else {
            textViewSummary.setVisibility(View.GONE);
        }
        return view;
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.passage_title) {
            Analytics.UIClick(readingsActivity, "passage_title");
            tryOpenIntegratedReader(((View) v.getParent().getParent()).getTag().toString());
        } else if (id == R.id.textViewSummary) {
            Analytics.UIClick(readingsActivity, "passage_summay");
            tryOpenIntegratedReader(((View) v.getParent()).getTag().toString());
        } else if (id == R.id.imageViewReadOffline) {
            Analytics.UIClick(readingsActivity, "passage_read_offline");
            tryOpenIntegratedReader(((View) v.getParent().getParent()).getTag().toString());
        } else if (id == R.id.imageListen) {
            Analytics.UIClick(readingsActivity, "passage_listen");
            openMp3(((View) v.getParent().getParent()).getTag().toString());
        } else if (id == R.id.imageViewReadOnline) {
            Analytics.UIClick(readingsActivity, "passage_read_online");
            openBibleGateway(((View) v.getParent().getParent()).getTag().toString());
        }
    }

    private void tryOpenIntegratedReader(String passage) {
        openIntegratedReader(passage);
    }

    private void openOfflineBible(String passage) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setType("vnd.android.cursor.item/vnd.uk.co.tekkies.bible.passage");
        intent.putExtra("passage", passage);
        //Look for plugin in packages
        PackageManager pm = readingsActivity.getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            // Intent can be serviced, try it.
            readingsActivity.startActivity(intent);
        } else {
            // install the off-line Bible
            Toast.makeText(readingsActivity, "The offline bible must be installed from Google Play.", Toast.LENGTH_LONG).show();
        }

    }

    private void openBibleGateway(String passage) {
        String url = "https://www.biblegateway.com/passage/?version=KJV&search=" + Uri.encode(passage);
        Intent webIntent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(url);
        webIntent.setData(uri);
        readingsActivity.startActivity(webIntent);
    }
    
    private void openIntegratedReader(String selectedPassage) {
        Intent intent = new Intent(readingsActivity, PassageActivity.class);
        ParcelableReadings passableReadings = new ParcelableReadings(passages, selectedPassage, readingsActivity.getSelectedDate());
        intent.putExtra(ParcelableReadings.PARCEL_NAME, passableReadings);
        readingsActivity.startActivity(intent);
    }

    private void openMp3(String passage) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setType("vnd.android.cursor.item/vnd.uk.co.tekkies.mp3bible.passage");
        intent.putExtra("passage", passage);
        //Look for plugin in packages
        PackageManager pm = readingsActivity.getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            // Intent can be serviced, try it.
            readingsActivity.startActivity(intent);
        } else {
            // install the off-line Bible
            Toast.makeText(readingsActivity, "The MP3 plugin must be installed from Google Play.", Toast.LENGTH_LONG).show();
        }
    }
}
