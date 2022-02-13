package com.android.foodify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.foodify.Adapters.AddToFavoriteAdapter;
import com.android.foodify.POJO.DataModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavoriteDishesActivity extends AppCompatActivity {

    private String userID;
    ArrayList<DataModel> dataModels;

    ListView listView;
    TextView errorText;
    private static AddToFavoriteAdapter adapter;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_dishes);
        getSupportActionBar().setTitle("Favorite Dishes");

        errorText = findViewById(R.id.tv_error);
        spinner = (ProgressBar) findViewById(R.id.progressBar);
        getFavorites();
    }

    private void getFavorites() {

        spinner.setVisibility(View.VISIBLE);
        dataModels = new ArrayList<DataModel>();
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference demografiRef = rootRef.child("Favorites").child(userID);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String recipeName = ds.child("recipeName").getValue(String.class);
                        String ingredients_name = ds.child("ingredients_name").getValue(String.class);
                        String imageBitmap = ds.child("imageBitmap").getValue(String.class);
                        String directions = ds.child("directions").getValue(String.class);
                        String videoId = ds.child("videoId").getValue(String.class);
//                        Log.e("TAG","recipeName : "+ recipeName);
//                        Log.e("TAG","imageBitmap : "+ imageBitmap);
//                        Log.e("TAG","ingredients_name : "+ ingredients_name);
                        spinner.setVisibility(View.GONE);
                        dataModels.add(new DataModel(recipeName, ingredients_name, imageBitmap,directions,videoId));
                        adapter = new AddToFavoriteAdapter(dataModels, getApplicationContext());
                        listView = findViewById(R.id.list);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                DataModel dataModel = dataModels.get(position);
                                Snackbar.make(view, dataModel.getName() + dataModel.getIngredients() + dataModel.getImageBitmap(), Snackbar.LENGTH_LONG)
                                        .setAction("No action", null).show();
                                Intent details = new Intent (FavoriteDishesActivity.this,FavoriteDishDetails.class);
                                details.putExtra("recipeName",recipeName);
                                details.putExtra("ingredients_name",ingredients_name);
                                details.putExtra("directions",directions);
                                details.putExtra("videoId",videoId);
                                startActivity(details);
                            }
                        });
                    }
                } else {
                    spinner.setVisibility(View.GONE);
                    errorText.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                spinner.setVisibility(View.GONE);
                errorText.setVisibility(View.VISIBLE);
            }
        };
        demografiRef.addListenerForSingleValueEvent(valueEventListener);


    }
}