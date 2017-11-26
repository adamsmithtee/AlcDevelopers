package com.example.adamsmith.alcdevelopers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Profile_details extends AppCompatActivity {
    String name;
    String profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_details);

        ImageView iconImageView = (ImageView) findViewById(R.id.icon);
        TextView nameTextView = (TextView) findViewById(R.id.username);
        TextView profileTextView = (TextView) findViewById(R.id.profile_url);

        //retrieve data from Intent
        name = getIntent().getStringExtra("name");
        Bitmap icon = getIntent().getParcelableExtra("icon");
        profile = getIntent().getStringExtra("profileUrl");

        iconImageView.setImageBitmap(icon);
        nameTextView.setText(name);
        profileTextView.setText(profile);


        Typeface custom_font = Typeface.createFromAsset(getAssets(),"fonts/LobsterTwo-Regular.otf");
       nameTextView.setTypeface(custom_font);

        Typeface profile_font = Typeface.createFromAsset(getAssets(),"fonts/GreatVibes-Regular.otf");
       profileTextView.setTypeface(profile_font);

        //set onClickListener on the profileUrl TextView
        final TextView profileUrl = (TextView)findViewById(R.id.profile_url);
        profileUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String urlString = profileUrl.getText().toString();
                //CONVERT the  String URL into a URI object (to pass into the intent constructor
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                //create an intent to choose a web browser
                startActivity(Intent.createChooser(webIntent, getString(R.string.choose_title)));

            }
        });
//set onClickListener on the button
        Button share = (Button)findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text, name, profile));
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_title)));
            }
        });

    }

    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_details, menu);
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
    } */
