# Foodify

## Abstract

This is an android application that is trained to detect multiple food items by just clicking the 
image of the food. When a user points his smartphone camera to a plate containing his/her 
meal, the app rapidly recognizes its recipe with the ingredients used. It also indicates the type 
of cuisine, estimate preparation time used in making that dish, total number of ingredients 
used and also the whole cooking process by means of the video.


## Description of the Project

Eating patterns and cooking culture have been evolving over time. In the past, food was mostly 
prepared at home, but nowadays we frequently consume food prepared by third parties (e.g. 
takeaways, catering and restaurants). Thus, the access to detailed information about prepared 
food is limited and, as a consequence, it is hard to know precisely what we eat. This application
will bring a massive ease through which the people are able to infer ingredients and cooking 
instructions from a prepared meal.


## List of Features:

1. Registration
Users can easily register into the app using their login credentials, or via their email ID. Other 
than that the app must also offer social media login as users find that pretty easy and use it 
often.
2. Try Food Camera (Take Photo)
User must be able to click the food image and after taking the picture following results must be 
generated:
* Show Ingredients name
* Show Recipe Name
* Show Recipe (Instructions)
* Show Preparation Time
* Show Cuisine type (Continental, Asian others)
* Video Link of Recipe (With each of the recipes, a video tutorial will be offered to 
the app users)
* Show Ingredient count (total number of ingredients used)
3. Share recipe
Allow your app users to share their favorite recipes with their friends on social media. T
4. Save Recipe
Enable your app users to save their favorite recipes and then access them from anywhere, and 
anytime




## Interface Preview 

  <p align="center">
  <img src="https://github.com/MohammadRizwan007/Foodify/blob/master/Assets/foodify-1.PNG" width="400"/>
  </p>
  <p align="center">
  <img src="https://github.com/MohammadRizwan007/Foodify/blob/master/Assets/foodify-2.PNG" width="400"/>
  </p>
  <p align="center">
  <img src="https://github.com/MohammadRizwan007/Foodify/blob/master/Assets/foodify-3.PNG" width="400"/>
  </p>
  
## Tools and Technologies ##

- Android Studio 3.3+
- Tensorflow Lite
- Youtube Player
- Firebase

## Tested On ##

* Infinix S4
* Samsung Galaxy Note 2
* Samsung Galaxy Alpha
* Samsung Galaxy Prime
* Samsung Galaxy S6
* Samsung A10s
* xiaomi mi a2



## Dependencies


``` 
dependencies {
...
   implementation 'androidx.appcompat:appcompat:1.3.1'
    implementation 'com.google.android.material:material:1.4.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.1'
    implementation 'org.tensorflow:tensorflow-lite-support:0.1.0'
    implementation 'org.tensorflow:tensorflow-lite-metadata:0.1.0'
    testImplementation 'junit:junit:4.+'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'

    //FireBase
    implementation platform('com.google.firebase:firebase-bom:29.0.0')
    implementation 'com.google.firebase:firebase-analytics'
    implementation 'com.google.firebase:firebase-auth'
    implementation 'com.google.firebase:firebase-firestore:24.0.0'
    implementation 'com.google.firebase:firebase-storage:20.0.0'
    implementation 'com.google.firebase:firebase-database:20.0.2'
    implementation 'com.pierfrancescosoffritti.androidyoutubeplayer:core:10.0.5'
}
```
