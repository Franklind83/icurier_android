package com.dev.todos.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.dev.todos.R;
import com.dev.todos.Util.UseMe;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


import java.util.ArrayList;

import static com.dev.todos.Url.WebService.BASE_URL;

public class ImageAdapter2 extends PagerAdapter {
    Context context;

    ArrayList<String> stringArrayList = new ArrayList<>();

    LayoutInflater mLayoutInflater;

    public ImageAdapter2(Context context) {
        this.context = context;
        this.stringArrayList.clear();
        this.stringArrayList = UseMe.stringArrayList;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return stringArrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_image_item, container, false);

        final ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);


            String path = stringArrayList.get(position);

            String mypath = BASE_URL + "media/" + path;
            Log.w("mypath", mypath);
            Picasso.get().load(mypath).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    imageView.setImageBitmap(bitmap);

                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });




        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
