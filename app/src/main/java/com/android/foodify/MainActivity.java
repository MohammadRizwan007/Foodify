package com.android.foodify;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.foodify.SharedPreferences.SharedPreferences;
import com.android.foodify.ml.Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1000;
    Uri image_uri;
    TextView cookingWithFun, result, recipe_time, no_of_ingredients, ingredients_name;
    ImageView openCamera, imageView;
    LinearLayout linearLayout, linearLayout2, linearLayout3;
    Button startCooking;
    int imageSize = 224;
    private FirebaseFirestore fStore;
    private String videoId = "";
    String directions_desc = "";
    int check = 0;
    private ImageButton favrt_btn;
    private ImageButton share_fvrt;
    private String userID;
    private FirebaseAuth fAuth;
    Bitmap image;
    String recipe = "";
    private ProgressBar spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Cooking With Fun");


        fStore = FirebaseFirestore.getInstance();
        openCamera = findViewById(R.id.openCamera);
        imageView = findViewById(R.id.imageView);
        linearLayout = findViewById(R.id.LinearLayout);
        linearLayout2 = findViewById(R.id.LinearLayout2);
        startCooking = findViewById(R.id.startCooking);
        cookingWithFun = findViewById(R.id.cookingWithFun);
        result = findViewById(R.id.result);
        recipe_time = findViewById(R.id.rec_time);
        no_of_ingredients = findViewById(R.id.ingredients);
        ingredients_name = findViewById(R.id.ingredients_name);
        favrt_btn = findViewById(R.id.add_fvrt);
        share_fvrt = findViewById(R.id.share_fvrt);
        spinner = (ProgressBar) findViewById(R.id.progressBar1);

        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCameraPermission();
            }
        });

        favrt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToFavorite();
            }
        });

        share_fvrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareFavorite();
            }
        });

    }

    public void checkCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                //Permission not enable request it
                String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                //Show popup to request permissions
                requestPermissions(permission, PERMISSION_CODE);
            } else {
                //Permission already granted
                openCamera();
            }
        } else {
            // system os < marshmallow
            openCamera();

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // This method is called when user press allow por deny to permission request popup
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE:
                if (grantResults.length > 0) {
                    // permission from POPup was granted
                    openCamera();
                } else {
                    // permission from POPup was denied
                    Toast.makeText(MainActivity.this, "Permission denied...", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void openCamera() {
        favrt_btn.setImageResource(R.drawable.ic_baseline_turned_in_not_24);
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From The Camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        //Camera Intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CAPTURE_CODE && resultCode == RESULT_OK) {

            check = 1;
            linearLayout.setVisibility(View.GONE);
            linearLayout2.setVisibility(View.VISIBLE);
            cookingWithFun.setVisibility(View.GONE);

            image = (Bitmap) data.getExtras().get("data");
            Log.e("Image Bitmap", "data" + image);
            int dimension = Math.min(image.getWidth(), image.getHeight());
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
            Log.e("Image Bitmap", "" + image);
            imageView.setImageBitmap(image);
            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
            classifyImage(image);

            startCooking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Directions.class);
                    intent.putExtra("videoId", videoId);
                    intent.putExtra("directions", directions_desc);
                    startActivity(intent);
                }
            });

        }

    }

    @SuppressLint("SetTextI18n")
    public void classifyImage(Bitmap image) {
        try {
            Model model = Model.newInstance(getApplicationContext());
            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            // get 1D array of 224 * 224 pixels in image
            int[] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());

            // iterate over pixels and extract R, G, and B values. Add to bytebuffer.
            int pixel = 0;
            for (int i = 0; i < imageSize; i++) {
                for (int j = 0; j < imageSize; j++) {
                    int val = intValues[pixel++]; // RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                }
            }

            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            Model.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();
            // find the index of the class with the biggest confidence.
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidences.length; i++) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }
            String[] classes = {"Aloo Gajar Matar Sabzi", "Chicken Shawarma", "Gajar Halwa", "Gulab Jamun", "Samosa", "Noodles"};

            result.setText(classes[maxPos]);

            recipe = classes[maxPos];
            DocumentReference docRef = fStore.collection("Ingredients").document(recipe);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null) {
                            recipe_time.setText(document.getString("Time"));
                            no_of_ingredients.setText(document.getString("Count"));
                            ingredients_name.setText(document.getString("Names"));
                            directions_desc = document.getString("Directions");
                            videoId = document.getString("Link");
//                            Log.e("LOGGER", "Names " + document.getString("Names"));
//                            Log.e("LOGGER", "Directions " + document.getString("Directions"));
//                            Log.e("LOGGER", "Link " + document.getString("Link"));
                        } else {
                            Toast.makeText(MainActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "get failed with"+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


            String s = "";
            for (int i = 0; i < classes.length; i++) {
                s += String.format("%s: %.1f%%\n", classes[i], confidences[i] * 100);
            }

            // Also Can Set Percentage of Similarity/Confidence From here
//            confidence.setText(s);

            // Releases model resources if no longer used.
            model.close();

        } catch (IOException e) {
            // TODO Handle the exception
        }

    }

    private void AddToFavorite() {

        Toast.makeText(MainActivity.this, "please Wait", Toast.LENGTH_SHORT).show();
        spinner.setVisibility(View.VISIBLE);
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference stores = database.getReference("Favorites");
        Map<String, Object> Favorites = new HashMap<>();
        Favorites.put("recipeName", result.getText().toString());
        Favorites.put("imageBitmap", String.valueOf(image_uri));
        Favorites.put("ingredients_name", ingredients_name.getText().toString());
        Favorites.put("directions", directions_desc);
        Favorites.put("videoId", videoId);

        stores.child(userID).push().setValue(Favorites).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                favrt_btn.setImageResource(R.drawable.ic_baseline_turned_in_24);
                spinner.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Add To Favorites SuccessFully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Some Error Occurred, Check Internet and Try Again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ShareFavorite (){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Ingredients : "+ingredients_name.getText().toString();
        String videoLink = "https://www.youtube.com/watch?v="+videoId;
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Recipe : "+result.getText().toString());
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Recipe : "+result.getText().toString()+"\n"+shareBody+"\n"+"Youtube Link: "+videoLink);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.logOut:
                FirebaseAuth.getInstance().signOut();
                SharedPreferences.removeValues(getApplicationContext());
                Toast.makeText(MainActivity.this, "You Have SuccessFully LogOut", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                break;

            case R.id.favorites:
                Intent favIntent = new Intent(MainActivity.this, FavoriteDishesActivity.class);
                startActivity(favIntent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (check == 1) {
            check = 0;
            linearLayout.setVisibility(View.VISIBLE);
            linearLayout2.setVisibility(View.GONE);
        } else {
            finishAffinity();
        }
    }

}