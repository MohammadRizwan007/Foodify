package com.android.foodify.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.foodify.POJO.DataModel;
import com.android.foodify.R;

import java.util.ArrayList;
import java.util.Date;

public class AddToFavoriteAdapter extends ArrayAdapter<DataModel> implements View.OnClickListener{

    private ArrayList<DataModel> dataSet;
    private Context context;

    // View lookup cache
    private static class ViewHolder {
        TextView tv_Name;
        TextView tv_Ingredients;
        ImageView img_dish;

    }

    public AddToFavoriteAdapter(ArrayList<DataModel> data, Context context) {
        super(context, R.layout.single_row_design, data);
        this.dataSet = data;
        this.context=context;
    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        final DataModel dataModel=(DataModel)object;

        switch (v.getId())
        {

        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DataModel dataModel = getItem(position);
        Log.e("DataModel","Name"+dataModel.getName());
        Log.e("DataModel","Ingredients"+dataModel.getIngredients());
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.single_row_design, parent, false);
            viewHolder.tv_Name =  convertView.findViewById(R.id.tv_rName);
            viewHolder.tv_Ingredients = convertView.findViewById(R.id.tv_rIngredients);
            viewHolder.img_dish =  convertView.findViewById(R.id.img_dish);
            result=convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;
        viewHolder.tv_Name.setText(dataModel.getName());
        viewHolder.tv_Ingredients.setText(dataModel.getIngredients());
        if (dataModel.getName().equals("Aloo Gajar Matar Sabzi")){
            viewHolder.img_dish.setImageResource(R.drawable.sabzi);
        }else if (dataModel.getName().equals("Chicken Shawarma")){
            viewHolder.img_dish.setImageResource(R.drawable.shawarma);
        }else if (dataModel.getName().equals("Gajar Halwa")){
            viewHolder.img_dish.setImageResource(R.drawable.gajarhalwa);
        }else if (dataModel.getName().equals("Gulab Jamun")){
            viewHolder.img_dish.setImageResource(R.drawable.gulabjamun);
        }else if (dataModel.getName().equals("Samosa")){
            viewHolder.img_dish.setImageResource(R.drawable.samosa);
        }else if (dataModel.getName().equals("Noodles")){
            viewHolder.img_dish.setImageResource(R.drawable.noodles);
        }
        // Return the completed view to render on screen
        return convertView;
    }



}
