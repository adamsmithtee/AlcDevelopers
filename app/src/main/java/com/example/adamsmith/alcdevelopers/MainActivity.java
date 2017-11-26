package com.example.adamsmith.alcdevelopers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DeveloperAdapter mAdapter;

    private static final String LOG_TAG = MainActivity.class.getName();

    private List<Developer> result;

    /**
     * URL to query the Git hub for developers information
     */
    private static final String GITHUB_DEVELOPERS_URL = "https://api.github.com/search/users?q=lagos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developer_main);


        //Get reference to the connectivity to the connectivityManager to check the state of the network
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //get details of the current active default data network
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        //if there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()){


            //find reference to the listview in the layout
        ListView developerListView = (ListView) findViewById(R.id.list);

        // Create a new adapter that takes the empty list of developers as input
        mAdapter = new DeveloperAdapter(this, new ArrayList<Developer>());
        //st the adapter on the listview
        developerListView.setAdapter(mAdapter);

        // start the AsyncTask to fetch the earthquake data
        DeveloperAsyncTask task = new DeveloperAsyncTask();
        task.execute(GITHUB_DEVELOPERS_URL);


            //set an onItemClicklistener on the list view to open an intent
            developerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent devintent = new Intent(MainActivity.this, Profile_details.class);
                    devintent.putExtra("name", result.get(position).getUsername());

                    ImageView iconimg = (ImageView)((LinearLayout) view).getChildAt(0);
                    Bitmap bitmap = ((BitmapDrawable) iconimg.getDrawable()).getBitmap();

                    devintent.putExtra("icon", bitmap);
                    devintent.putExtra("profileUrl", result.get(position).getUrl());
                    startActivity(devintent);
                }
            });



        }
        else{
            setContentView(R.layout.no_connection);

        }

    }

  /**  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    } **/

  private class DeveloperAsyncTask extends AsyncTask<String, Void, List<Developer>> {
      @Override
      protected List<Developer> doInBackground(String... urls) {
          //dnt perform the request if there are no urls, or the first url is null
          if (urls.length < 1 || urls[0] == null) {
              return null;
          }
          result = QueryUtils.fetchdeveloperlistData(urls[0]);
          return result;
      }

      /**
       * Update the screen with the given earthquake (which was the result of the
       * {@link DeveloperAsyncTask}).
       */
      @Override
      protected void onPostExecute(List<Developer> data) {

          //Hide loading Indicator because the data has been loaded
          View loadingIndicator = findViewById(R.id.loading_indicator);
          loadingIndicator.setVisibility(View.GONE);
          // clear the adapter of the previous earthquake data
          mAdapter.clear();
          //
          if (data != null && !data.isEmpty()) {
              mAdapter.addAll(data);
          }
      }
  }
}
