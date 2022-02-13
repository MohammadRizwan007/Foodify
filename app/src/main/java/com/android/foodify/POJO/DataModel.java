package com.android.foodify.POJO;

public class DataModel {

    String Name;
    String Ingredients;
    String ImageBitmap;
    String Directions;
    String VideoId;

    public DataModel(String Name, String Ingredients, String ImageBitmap, String Directions, String VideoId) {
        this.Name = Name;
        this.Ingredients = Ingredients;
        this.ImageBitmap = ImageBitmap;
        this.Directions = Directions;
        this.VideoId = VideoId;
    }

    public String getName() {
        return Name;
    }

    public String getIngredients() {
        return Ingredients;
    }

    public String getImageBitmap() {
        return ImageBitmap;
    }

    public String getDirections() {
        return Directions;
    }

    public String getVideoId() {
        return VideoId;
    }

}
