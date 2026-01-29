/*
Copyright 2026 James Robinson. Based on Readings by Andy Joiner, 2013.

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

package org.navigatebyfaith.rrreadings.day;

import java.util.ArrayList;

import org.navigatebyfaith.rrreadings.Injector;
import org.navigatebyfaith.rrreadings.R;
import org.navigatebyfaith.rrreadings.adapter.PortionArrayAdapter;
import org.navigatebyfaith.rrreadings.model.Passage;
import org.navigatebyfaith.rrreadings.model.Prefs;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.preference.PreferenceManager;
import androidx.fragment.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class DayFragment extends Fragment implements DayView {

    private DayPresenter presenter;

    public static final String ARG_YEAR = "year";
    public static final String ARG_MONTH = "month";
    public static final String ARG_DAY = "day";

    ArrayList<Passage> listItems = new ArrayList<Passage>();
    PortionArrayAdapter adapter;
    private Boolean showSummary = true;

    public DayFragment() {
        presenter = Injector.getDayPresenter(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Bundle args = getArguments();
        presenter.setCalendar(args.getInt(ARG_YEAR), args.getInt(ARG_MONTH), args.getInt(ARG_DAY));

        adapter = new PortionArrayAdapter(getActivity(), listItems);
        ListView listViewMain = (ListView) view.findViewById(R.id.listView);
        listViewMain.setAdapter(adapter);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        showSummary = settings.getBoolean(Prefs.PREF_SHOW_SUMMARY, true);
    }

    @Override
    public View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.readings_fragment, container, false);

        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(insets.left, insets.top, insets.right, insets.bottom);
            return windowInsets;
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.reLoad();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_readings, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_summary) {
            doToggleSummary();
            return true;
        } else {
            return false;
        }
    }

    private void doToggleSummary() {
        showSummary = !showSummary;
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(Prefs.PREF_SHOW_SUMMARY, showSummary);
        editor.commit();
        presenter.reLoad();
    }

    @Override
    public void clearList() {
        listItems.clear();
    }

    @Override
    public void addItem(Passage passage) {
        listItems.add(passage);
    }

    @Override
    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }
}