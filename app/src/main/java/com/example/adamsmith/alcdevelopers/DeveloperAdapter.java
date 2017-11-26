package com.example.adamsmith.alcdevelopers;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by adamsmith on 10/10/2017.
 */
public class DeveloperAdapter extends ArrayAdapter<Developer> {

    // stores already downloaded Bitmaps for reuse
    private Map<String, Bitmap> bitmaps = new HashMap<>();

    public DeveloperAdapter(Activity context, ArrayList<Developer> developerList) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, developerList);

    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The position in the list of data that should be displayed in the
     *                    list item view.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.developer_list, parent, false);
        }

        // Get the {@link Word} object located at this position in the list
        Developer currentDeveloper = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
       TextView userTextView = (TextView) listItemView.findViewById(R.id.username);
        // Get the username from the current Earthquake object and
        // set this text on the name TextView
       userTextView.setText(currentDeveloper.getUsername());

        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(),"fonts/LobsterTwo-Regular.otf");
       userTextView.setTypeface(custom_font);

       //for img icon of the developer
        ImageView iconImageView = (ImageView) listItemView.findViewById(R.id.icon);

        // if developer icon is already downloaded, use it;
        // otherwise, download icon in a separate thread
        if (bitmaps.containsKey(currentDeveloper.getProfileImage())) {
            iconImageView.setImageBitmap(bitmaps.get(currentDeveloper.getProfileImage()));
        }
            else
        {
            new DownloadImageTask(iconImageView)
                    .execute(currentDeveloper.getProfileImage());
        }

        return listItemView;
    }

    // AsyncTask to load developer icons in a separate thread
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        private ImageView imageView; // displays the thumbnail

        // store ImageView on which to set the downloaded Bitmap
        public DownloadImageTask(ImageView view) {
            this.imageView = view;
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            HttpURLConnection connection = null;

            try {
                URL url = new URL(params[0]); // create URL for image

                // open an HttpURLConnection, get its InputStream
                // and download the image
                connection = (HttpURLConnection) url.openConnection();

                try(InputStream inputStream = connection.getInputStream()) {
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    bitmaps.put(params[0], bitmap); // cache for later use

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect(); // close the HttpURLConnection
            }


            return bitmap;
        }

        // set developer image in list item
        @Override
        public void onPostExecute(Bitmap bitmap){
            imageView.setImageBitmap(bitmap);
        }
    }

    }
