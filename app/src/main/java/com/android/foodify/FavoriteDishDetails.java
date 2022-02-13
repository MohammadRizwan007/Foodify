package com.android.foodify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class FavoriteDishDetails extends AppCompatActivity {

    private TextView dish_Name;
    private TextView dish_Ingredients;
    private TextView dish_directions;
    YouTubePlayerView youTubePlayerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_favorite_dish_details);


        dish_Name = (TextView) findViewById(R.id.dish_Name);
        dish_Ingredients = (TextView) findViewById(R.id.ingredients_detail);
        dish_directions = (TextView) findViewById(R.id.directions_detail);
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            String recipeName = extras.getString("recipeName");
            String ingredients_name = extras.getString("ingredients_name");
            String directions = extras.getString("directions");
            String videoId = extras.getString("videoId");
            dish_Name.setText(recipeName);
            dish_Ingredients.setText(ingredients_name);
            dish_directions.setText(directions);
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    youTubePlayer.loadVideo(videoId, 0);
                    Log.e("Video", "ID :: " + videoId);
                }
            });
        }





    }
}