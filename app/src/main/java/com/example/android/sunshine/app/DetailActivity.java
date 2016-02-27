/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.sunshine.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ShareActionProvider;
import android.widget.TextView;

public class DetailActivity extends ActionBarActivity {

    public static final String FORECAST_EXTRA = "DetailActivity.FORECAST_EXTRA";

    private ShareActionProvider mShareActionProvider;

    public String getForecast() {
        return getIntent().getStringExtra(FORECAST_EXTRA);
    }

    /**
     * Use this method to create the intent to start this activity.
     *
     * @param forecast String whith the forecast to be displayed
     * @param context  The context to create the activity
     * @return An intent with all the information to start the activity
     */
    public static Intent createActivityIntent(final String forecast, final Context context) {
        final Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(FORECAST_EXTRA, forecast);
        return intent;
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    public void doShare(Intent shareIntent) {
        // When you want to share set the share intent.
        mShareActionProvider.setShareIntent(shareIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.menu_item_share) {
            final String sharedForecast = String.format("%s #SunshineApp", getForecast());
            Intent shareIntent = new Intent();
            shareIntent.putExtra(Intent.EXTRA_TEXT, sharedForecast);
            setShareIntent(shareIntent);
            doShare(shareIntent);
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            final String forecast = ((DetailActivity)getActivity()).getForecast();
                ((TextView) rootView.findViewById(R.id.detail_text))
                        .setText(forecast);
            return rootView;
        }
    }
}
